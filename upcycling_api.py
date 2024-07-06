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

# Route pour obtenir un produit et ses informations
@app.route('/products/<int:barcode>', methods=['GET'])
def get_product(barcode):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)

    # Récupérer les informations du produit
    product_query = """
    SELECT p.Barcode, p.ProductName, p.ProductScore, p.ImageUrl
    FROM products p
    WHERE p.Barcode = %s
    """
    cursor.execute(product_query, (barcode,))
    product = cursor.fetchone()

    if not product:
        cursor.close()
        connection.close()
        return jsonify({'error': 'Product not found'}), 404

    # Récupérer les types de packaging et les scores pratiques
    packaging_query = """
    SELECT pt.TypeName, pt.Quantity, pt.PracticalScore
    FROM packagingtypes pt
    JOIN productpackaging pp ON pt.PackagingTypeID = pp.PackagingTypeID
    WHERE pp.Barcode = %s
    """
    cursor.execute(packaging_query, (barcode,))
    packagings = cursor.fetchall()

    # Calculer le score moyen pratique
    practical_scores = [p['PracticalScore'] for p in packagings]
    if practical_scores:
        average_score = sum(practical_scores) / len(practical_scores)
    else:
        average_score = 0

    # Mettre à jour le score du produit
    product['ProductScore'] = average_score

    # Récupérer les idées de réutilisation pour chaque packaging
    for packaging in packagings:
        reuse_ideas_query = """
        SELECT ri.IdeaType, ri.IdeaDescription, ri.Instructions, ri.IdeaURL
        FROM reuseideas ri
        WHERE ri.PackagingTypeID = (SELECT PackagingTypeID FROM packagingtypes WHERE TypeName = %s)
        """
        cursor.execute(reuse_ideas_query, (packaging['TypeName'],))
        reuse_ideas = cursor.fetchall()
        packaging['ReuseIdeas'] = reuse_ideas

    product['Packagings'] = packagings

    cursor.close()
    connection.close()

    return jsonify(product)

    
if __name__ == '__main__':
    app.run(debug=True)
