extends Button

func _ready() -> void:
	# On se connecte automatiquement au SceneManager au démarrage
	self.pressed.connect(SceneManager.next_level)
	
	
func _on_pressed() -> void:
	print("test")
