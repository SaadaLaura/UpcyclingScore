from flask import Flask, request, jsonify
import mysql.connector
import configparser

config = configparser.ConfigParser()
config.read('config.ini')
password = config['DEFAULT']['Password']

app = Flask(__name__)

# Configuration de la base de données
db_config = {
    'user': 'root',
    'password': config['DEFAULT']['Password'],
    'host': 'localhost',
    'database': 'upcycling'
}

def get_db_connection():
    return mysql.connector.connect(**db_config)

@app.route('/')
def home():
    return "API for Product Upcycling"

# Route pour ajouter un produit
@app.route('/products', methods=['POST'])
def add_product():
    data = request.json
    product_name = data['productname']
    barcode = data['barcode']
    packaging_types = data['packaging_types']

    connection = get_db_connection()
    cursor = connection.cursor()

    # Insérer le produit
    insert_product_query = "INSERT INTO Products (ProductName, Barcode) VALUES (%s, %s)"
    cursor.execute(insert_product_query, (product_name, barcode))
    product_id = cursor.lastrowid

    # Insérer les types d'emballages associés
    for packaging_type_id in packaging_types:
        insert_packaging_query = "INSERT INTO ProductPackaging (ProductID, PackagingTypeID) VALUES (%s, %s)"
        cursor.execute(insert_packaging_query, (product_id, packaging_type_id))

    connection.commit()
    cursor.close()
    connection.close()

    return jsonify({'message': 'Product added successfully', 'product_id': product_id})

# Route pour obtenir les idées de réutilisation d'un produit
@app.route('/products/<int:product_id>/reuseideas', methods=['GET'])
def get_reuse_ideas(product_id):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)

    reuse_ideas_query = """
    SELECT p.ProductName, r.IdeaDescription
    FROM Products p
    JOIN ProductPackaging pp ON p.ProductID = pp.ProductID
    JOIN PackagingTypes pt ON pt.PackagingTypeID = pp.PackagingTypeID
    JOIN ReuseIdeas r ON r.PackagingTypeID = pt.PackagingTypeID
    WHERE p.ProductID = %s
    """
    cursor.execute(reuse_ideas_query, (product_id,))
    result = cursor.fetchall()

    cursor.close()
    connection.close()

    return jsonify(result)

# Route pour obtenir un produit
@app.route('/products/<int:barcode>', methods=['GET'])
def get_product(barcode):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)

    product_query = """
    SELECT p.Barcode, p.ProductName AS name, p.ProductScore AS score, p.ImageURL AS urlImageProduct,
           pt.TypeName AS packagingType, pt.Quantity AS quantity,
           ri.IdeaType AS ideaType, ri.IdeaDescription AS ideaDescription, ri.Instructions AS ideaInstructions, ri.IdeaURL AS ideaURL
    FROM Products p
    JOIN ProductPackaging pp ON p.Barcode = pp.Barcode
    JOIN PackagingTypes pt ON pp.PackagingTypeID = pt.PackagingTypeID
    LEFT JOIN ReuseIdeas ri ON pt.PackagingTypeID = ri.PackagingTypeID
    WHERE p.Barcode = %s
    """
    cursor.execute(product_query, (barcode,))
    results = cursor.fetchall()

    cursor.close()
    connection.close()

    if results:
        # Préparer la structure JSON attendue
        product_data = {
            "barcode": results[0]['Barcode'],
            "name": results[0]['name'],
            "score": results[0]['score'],
            "urlImageProduct": results[0]['urlImageProduct'],
            "packagings": []
        }

        # Structure de données temporaire pour stocker les informations d'emballage
        temp_packagings = {}
        
        for result in results:
            packaging_type = result['packagingType']
            quantity = result['quantity']
            idea_type = result['ideaType']
            idea_description = result['ideaDescription']
            idea_instructions = result['ideaInstructions']
            idea_url = result['ideaURL']

            if packaging_type not in temp_packagings:
                temp_packagings[packaging_type] = {
                    "packagingType": packaging_type,
                    "quantity": quantity,
                    "reuseIdeas": []
                }

            if idea_type and idea_description and idea_instructions and idea_url:
                temp_packagings[packaging_type]["reuseIdeas"].append({
                    "ideaType": idea_type,
                    "ideaDescription": idea_description,
                    "ideaInstructions": idea_instructions,
                    "ideaURL": idea_url
                })

        # Ajouter les données d'emballage structurées à product_data
        for packaging in temp_packagings.values():
            product_data["packagings"].append(packaging)

        return jsonify(product_data)
    else:
        return jsonify({'error': 'Product not found'}), 404

    
if __name__ == '__main__':
    app.run(debug=True)
