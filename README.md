# Upcycling

**Projet de 4e année — Efrei Paris**

Upcycling est une **application mobile Android dédiée au réemploi des emballages et produits du quotidien**. L'application permet aux utilisateurs de scanner le code-barres d'un produit afin d'obtenir des informations sur ses possibilités de réutilisation.

Pour chaque produit reconnu, l'application affiche un **score sur 20** ainsi que différentes idées de réutilisation, classées en deux catégories : **pratique** et **artistique**.

## Fonctionnalités

### 📷 Scan des produits

* Scan du code-barres d'un produit à l'aide de la caméra du téléphone.
* Récupération des informations du produit via une API.
* Affichage du nom, de la photo, du type d'emballage et du score de réutilisation.
* Gestion des produits non référencés et des codes-barres non reconnus.

### ♻️ Réutilisation des produits

* Affichage des différentes possibilités de réutilisation d'un produit.
* Classement des idées en deux catégories : **réutilisation pratique** et **réutilisation artistique**.
* Sélection du type de réutilisation via des onglets.
* Sélection de l'emballage lorsque plusieurs emballages sont disponibles.
* Accès aux instructions détaillées d'une idée de réutilisation via une page web.

### 🕘 Historique

* Consultation des produits précédemment scannés.
* Accès au détail d'un produit depuis l'historique.
* Sélection d'un ou plusieurs produits.
* Suppression des produits sélectionnés de l'historique.

## Technologies utilisées

### Application mobile

* **Java** — Développement de la logique de l'application.
* **XML** — Conception des interfaces.
* **Android Studio** — Environnement de développement.

### Backend et données

* **Python / Flask** — Développement de l'API permettant de récupérer les informations des produits.
* **MySQL** — Stockage des données.
* **SQL** — Gestion et manipulation des données.

## Objectifs

Upcycling a pour objectif de **sensibiliser au réemploi des produits et emballages** en proposant des idées concrètes et accessibles pour leur donner une seconde utilisation.

L'application permet ainsi de :

* Découvrir facilement les possibilités de réutilisation d'un produit.
* Encourager la réutilisation plutôt que le simple abandon des emballages.
* Proposer des idées pratiques ou créatives adaptées aux différents emballages.
* Centraliser les produits déjà consultés grâce à un historique.

## Équipe

| Équipe   | Membres                                 | Contributions                                                                                                                                           |
| -------- | --------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **IT**   | Vadim Bernard, Robin Lucas, Laura Saada | Développement de l'application Android, notamment du scanner, de l'interface de détail des produits, des idées de réutilisation et de l'historique.     |
| **Data** | Louis Caillarec, Paul Jouvanceau        | Conception et alimentation de la base de données, développement de l'API de récupération des informations produits et support au développement backend. |

