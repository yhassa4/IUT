extends Sprite2D

func _process(delta):
	# Tourne de 5 radians par seconde (ajuste le chiffre pour la vitesse)
	rotation += 5 * delta




func _on_area_2d_body_entered(body: Node2D) -> void:
	# On vérifie si l'objet qui a touché la scie possède la fonction "die"
	if body.has_method("die"):
		body.die() 
