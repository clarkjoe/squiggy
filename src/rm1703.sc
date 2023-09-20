;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_BEDROOM_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1703.shm)
(use Actor)
(use Cycle)
(use Door)
(use Feature)
(use Game)
(use Main)
(use Polygon)
(use System)
(use PolyPath)
(use Scaler)

(public
	rm1703 0
)

(instance rm1703 of Room
	(properties
		picture scriptNumber
		noun N_ROOM
	)
	
	(method (init)
		(gRoom addObstacle: (&getpoly {contained}))
		(super init:)
		(switch gPreviousRoomNumber
			(else
				(SetUpEgo -1 0)
				(if (not (gEgo has: INV_AXE)) 
					(axe init: approachVerbs: V_DO)
				)
				(self setScript: entrance)
			)
		)
		(window setOnMeCheck: omcCOLORS ctlPURPLE init:)
		(shelf setOnMeCheck: omcCOLORS ctlNAVY init:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		
		(= egoOnControl (gEgo onControl:))
		
		(if (& ctlLIME egoOnControl) (rm1703 setScript: exit))
	)
)

(instance entrance of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(gEgo init: setScale: Scaler 75 75 0 1)
				(gEgo posn: 81 134 view: ROSELLA_PEASANT_DESCEND_ASCEND_VIEW setLoop: 4 setCel: 0)
				(gEgo setMotion: PolyPath 120 134 self)
			)
			(1
				(gGame handsOn:)
				(gEgo view: ROSELLA_PEASANT_VIEW)
				(rm1703 setScript: RoomScript)
			)
		)
	)
)

(instance exit of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(gEgo view: ROSELLA_PEASANT_DESCEND_ASCEND_VIEW setLoop: 1 setCel: 0)
				(gEgo setMotion: PolyPath 80 134 self)
			)
			(1
				(gGame newRoom: CABIN_ENTRANCE_SCRIPT)
			)
		)
	)
)

(instance axe of Prop
	(properties
		view 900
		x 65
		y 148
		approachX 65
		approachY 158
		signal ignAct
		loop 0
		cel 0
		noun N_AXE
		priority -1
	)

	(method (doVerb theVerb)
		(switch theVerb
			(V_DO
				(rm1703 setScript: getAxe)
			)
			(else 
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance getAxe of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(
				(gEgo get: INV_AXE)
				(axe dispose:)
				(self cue:)
			)
			(
				(rm1703 setScript: RoomScript)
			)
		)
	)
)

(instance window of Feature
	(properties
		noun N_WINDOW
	)
)

(instance shelf of Feature
	(properties
		noun N_SHELF
	)
)