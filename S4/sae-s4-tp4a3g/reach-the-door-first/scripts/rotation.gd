extends Sprite2D

func _process(delta):
	# Tourne de 5 radians par seconde (ajuste le chiffre pour la vitesse)
	rotation += 5 * delta
