package mvc.modele.service;

/**
 * Enumération représentant les différents types de repas proposés dans les menus.
 *
 * VEGETARIEN : menu sans viande, mais contenant éventuellement des produits laitiers ou des œufs.
 * VEGAN      : menu sans aucun produit d'origine animale.
 * CLASSIQUE  : menu standard pouvant contenir tout type d'ingrédients (viande, poisson, etc.).
 */
public enum TypeRepas {
	VEGETARIEN, VEGAN, CLASSIQUE;

}