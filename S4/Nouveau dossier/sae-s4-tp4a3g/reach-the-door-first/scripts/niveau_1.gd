extends Node2D

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta: float) -> void:
	pass


func _on_porte_sortie_body_entered(body: Node2D) -> void:
	# On vérifie si l'objet qui entre dans la zone s'appelle bien "Player"
	if body.name == "Player":
		print("Le joueur a atteint la porte ! Victoire !")
