extends Area2D

@export var points_reward: int = 1

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(_delta: float) -> void:
	pass


func _on_body_entered(body):
	if "team_name" in body:
		print("Joueur sorti")
		# On donne les points dynamiquement selon la valeur réglée dans l'inspecteur
		TeamManager.add_points(body.team_name, points_reward)
		
		# Ensuite on dit au SceneManager de passer au niveau suivant
		# (Assure-toi d'appeler la bonne fonction pour changer de niveau)
		if SceneManager:
			SceneManager.next_level()
		else:
			get_tree().change_scene_to_file("res://scenes/endgame.tscn")
