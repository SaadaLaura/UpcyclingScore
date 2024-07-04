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

# Route pour obtenir un produit avec un id donné
@app.route('/products/<int:product_id>', methods=['GET'])
def get_product(product_id):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)

    product_query = """
    SELECT p.ProductID, p.ProductName, p.Barcode, p.ProductScore, 
           GROUP_CONCAT(pt.TypeName) as PackagingTypes
    FROM Products p
    LEFT JOIN ProductPackaging pp ON p.ProductID = pp.ProductID
    LEFT JOIN PackagingTypes pt ON pp.PackagingTypeID = pt.PackagingTypeID
    WHERE p.ProductID = %s
    GROUP BY p.ProductID
    """
    cursor.execute(product_query, (product_id,))
    result = cursor.fetchone()

    cursor.close()
    connection.close()

    if result:
        return jsonify(result)
    else:
        return jsonify({'error': 'Product not found'}), 404
    
if __name__ == '__main__':
    app.run(debug=True)
