extends Node

# On précharge les scènes pour éviter les lags
const SCENES = {
	"death": preload("res://scenes/particleDeath.tscn"),
	"jump": preload("res://scenes/particleJump.tscn")
}

func spawn(type: String, pos: Vector2):

	if SCENES.has(type):
		
		var fx = SCENES[type].instantiate()
		print("Position Joueur: ", pos, " | Position Particule: ", fx.global_position)
		
		
		# On l'ajoute à la scène courante (le monde) pour qu'il soit indépendant
		get_tree().current_scene.add_child(fx)
		
		fx.global_position = pos
		
		fx.emitting = true
	else:
		print("Erreur : Le type de particule '", type, "' n'existe pas dans le Manager.")
