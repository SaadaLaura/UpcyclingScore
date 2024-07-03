# Product Upcycling API

Cette API permet de gérer des produits et leurs idées de réutilisation en se basant sur leurs types d'emballage. Elle est construite avec Flask et MySQL.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les éléments suivants :

- Python 3.6 ou supérieur
- MySQL
- pip (gestionnaire de paquets Python)

## Installation

1. Clonez ce dépôt :

    ```sh
    git clone <url_du_dépôt>
    cd <nom_du_dépôt>
    ```

2. Créez un environnement virtuel et activez-le :

    ```sh
    python -m venv venv
    source venv/bin/activate  # Sur Windows, utilisez `venv\Scripts\activate`
    ```

3. Installez les dépendances :

    ```sh
    pip install Flask mysql-connector-python
    ```

## Configuration de la base de données

1. Créez une base de données MySQL et les tables nécessaires :

    ```sql
    CREATE DATABASE upcycling;

    USE upcycling;

    CREATE TABLE Products (
        ProductID INT AUTO_INCREMENT PRIMARY KEY,
        ProductName VARCHAR(255) NOT NULL,
        Barcode VARCHAR(255) NOT NULL,
        ProductScore DECIMAL(5,2) DEFAULT 0
    );

    CREATE TABLE PackagingTypes (
        PackagingTypeID INT AUTO_INCREMENT PRIMARY KEY,
        TypeName VARCHAR(255) NOT NULL,
        PracticalScore DECIMAL(5,2) NOT NULL,
        ArtisticScore DECIMAL(5,2) NOT NULL
    );

    CREATE TABLE ProductPackaging (
        ProductPackagingID INT AUTO_INCREMENT PRIMARY KEY,
        ProductID INT,
        PackagingTypeID INT,
        FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
        FOREIGN KEY (PackagingTypeID) REFERENCES PackagingTypes(PackagingTypeID)
    );

    CREATE TABLE ReuseIdeas (
        IdeaID INT AUTO_INCREMENT PRIMARY KEY,
        PackagingTypeID INT,
        IdeaDescription TEXT,
        FOREIGN KEY (PackagingTypeID) REFERENCES PackagingTypes(PackagingTypeID)
    );
    ```

2. Mettez à jour la configuration de la base de données dans le fichier `app.py` :

    ```python
    db_config = {
        'user': 'root',
        'password': '',
        'host': 'localhost',
        'database': 'upcycling'
    }
    ```

## Exécution de l'API

1. Démarrez l'application Flask :

    ```sh
    python app.py
    ```

2. L'API sera accessible à l'adresse : `http://127.0.0.1:5000`

## Routes de l'API

### Accueil

- **URL** : `/`
- **Méthode** : `GET`
- **Description** : Affiche un message d'accueil.

### Ajouter un produit

- **URL** : `/products`
- **Méthode** : `POST`
- **Description** : Ajoute un nouveau produit avec ses types d'emballage.
- **Corps de la requête** (JSON) :
    ```json
    {
        "productname": "IceTea Lipton",
        "barcode": "3168930171058",
        "packaging_types": [1, 2]
    }
    ```

### Obtenir les idées de réutilisation d'un produit

- **URL** : `/products/<int:product_id>/reuseideas`
- **Méthode** : `GET`
- **Description** : Retourne les idées de réutilisation pour un produit spécifique.

### Obtenir les détails d'un produit

- **URL** : `/products/<int:product_id>`
- **Méthode** : `GET`
- **Description** : Retourne les détails d'un produit spécifique.

## Tester l'API

### Utilisation de Postman

1. **Ajouter un produit** :
   - Méthode : `POST`
   - URL : `http://127.0.0.1:5000/products`
   - Corps de la requête (JSON) :
     ```json
     {
         "productname": "IceTea Lipton",
         "barcode": "3168930171058",
         "packaging_types": [1, 2]
     }
     ```

2. **Obtenir les idées de réutilisation d'un produit** :
   - Méthode : `GET`
   - URL : `http://127.0.0.1:5000/products/1/reuseideas`

3. **Obtenir les détails d'un produit** :
   - Méthode : `GET`
   - URL : `http://127.0.0.1:5000/products/1`

### Utilisation de cURL

1. **Ajouter un produit** :
    ```sh
    curl -X POST http://127.0.0.1:5000/products -H "Content-Type: application/json" -d '{
        "productname": "IceTea Lipton",
        "barcode": "3168930171058",
        "packaging_types": [1, 2]
    }'
    ```

2. **Obtenir les idées de réutilisation d'un produit** :
    ```sh
    curl -X GET http://127.0.0.1:5000/products/1/reuseideas
    ```

3. **Obtenir les détails d'un produit** :
    ```sh
    curl -X GET http://127.0.0.1:5000/products/1
    ```

## Conclusion

Cette API vous permet de gérer des produits et leurs idées de réutilisation en se basant sur leurs types d'emballage. Vous pouvez l'étendre et l'améliorer en ajoutant plus de fonctionnalités selon vos besoins.
