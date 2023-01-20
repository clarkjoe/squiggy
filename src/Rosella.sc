;;; Sierra Script 1.0 - (do not remove this comment)
(script# ROSELLA_SCRIPT)
(include game.sh)

(use Main)

(use Talker)
(use Actor)
(use Reverse)

(public
	rosellaTalker 0
)

(instance rosellaTalker of Talker
	(properties
		x 10
		y 25
		view VIEW_ROSELLA_TALKER
		loop 0
		talkWidth 150
		back 5
		textX 120
		textY 10
	)

	(method (init)
		(= font gFont)
		; For talkers, we can optionally set a cycler on the eyes.
		; If there is no cycler, the Blink cycler will be used.
		(super
			init: rosellaBust rosellaEyes rosellaMouth &rest
		)
	)

	(method (dispose)
		(super dispose: &rest)
	)
)

(instance rosellaBust of Prop
	(properties
		view VIEW_ROSELLA_TALKER
	)
)

(instance rosellaEyes of Prop
	(properties
		cycleSpeed 10
		nsTop 25
		nsLeft 30
		view VIEW_ROSELLA_TALKER
		loop LOOP_EYE
	)
)

(instance rosellaMouth of Prop
	(properties
		nsTop 33
		nsLeft 30
		view VIEW_ROSELLA_TALKER
		loop LOOP_MOUTH
	)
)

