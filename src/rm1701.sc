;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_ENTRANCE_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1701.shm)
(use Actor)
(use Cycle)
(use Door)
(use Feature)
(use Game)
(use Main)
(use Polygon)
(use System)
(use Scaler)
(use StopWalk)
(use PolyPath)

(public
	rm1701 0
)

(instance rm1701 of Room
	(properties
		picture scriptNumber
		noun N_ROOM
	)

	(method (init)
		(gRoom addObstacle: (&getpoly {contained}))
		(gRoom addObstacle: (&getpoly {globe}))
		(super init:)
		(SetUpEgo -1 0)
		(= gKeyholePic CABIN_KEYHOLE_PIC_SLEEP)
		(switch gPreviousRoomNumber
			(CABIN_KITCHEN_SCRIPT
				(self setScript: RoomScript)
				(gEgo posn: 259 130 loop: STILL_LOOP cel: STILL_LEFT_CEL)
			)
			(CABIN_BEDROOM_SCRIPT
				(self setScript: enterFromBedroom)
			)
			(CABIN_CLOSET_SCRIPT
				(self setScript: RoomScript)
				(gEgo posn: 204 121 loop: STILL_LOOP cel: STILL_DOWN_CEL)
			)
			(else
				(self setScript: RoomScript)
				(gEgo posn: 44 145)
			)
		)
		(gEgo init: setScale: Scaler 85 85 150 120)
		(gEgo get: INV_BONE)
		(gGame handsOn:)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit &tmp egoOnControl)
		(super doit:)
		(= egoOnControl (gEgo onControl:))
		
		(cond
			((& ctlLIME egoOnControl)
				(gRoom newRoom: CABIN_CLOSET_SCRIPT)
			)
			((& ctlCYAN egoOnControl)
				(rm1701 setScript: exitToBedroom)
			)
			((& ctlFUCHSIA egoOnControl)
				(gRoom newRoom: CABIN_KITCHEN_SCRIPT)
			)
			((& ctlNAVY egoOnControl)
				(if (< (gEgo heading:) 180)
					(gEgo setLoop: 0)
				else
					(gEgo setLoop: 1)
				)
			)
			((& ctlGREEN egoOnControl)
				(if (== gKeyholePic CABIN_KEYHOLE_PIC_SLEEP) (rm1701 setScript: ogreEatsRosella))
			)
		)
	)
	
	(method (changeState newState &tmp nextHenX nextHenY)
		(= state newState)
		(= nextHenX (Random 72 168))
		(= nextHenY (Random 144 166))
		(switch state
			(0
				(if (== gKeyholePic CABIN_KEYHOLE_PIC_SLEEP) (self cue:))
			)
			(1
				(ogre init: setMotion: Forward setSpeed: 60)
				(self cue:)
			)
			(2
				(if (not (gEgo has: INV_HEN)) 
					(hen posn: 87 159 init: ignoreActors: FALSE setSpeed: 3 setCycle: StopWalk -1)
					(self cue:)
				)
			)
			(3
				(hen setMotion: PolyPath nextHenX nextHenY self)
			)
			(4
				(= seconds 1)
			)
			(5
				(self changeState: 3)
			)
		)
	)
)

(instance enterFromBedroom of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(gEgo init: setScale: Scaler 75 75 0 1)
				(gEgo posn: 218 43)
				(gEgo setMotion: PolyPath 183 43 self)
			)
			(1
				(gGame handsOn:)
				(rm1701 setScript: RoomScript)
			)
		)
	)
)

(instance exitToBedroom of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(gGame handsOff:)
				(gEgo setMotion: PolyPath 222 43 self)
			)
			(1
				(gRoom newRoom: CABIN_BEDROOM_SCRIPT)
			)
		)
	)
)

(instance ogre of Actor
	(properties
		view OGRE_SLEEPING_VIEW
		x 242
		y 170
		signal ignAct
		noun N_OGRE
	)
)

(instance hen of Actor
	(properties
		view HEN_VIEW
		signal ignAct
		loop 0
		cel 0
		noun N_HEN
	)
	
	(method (doVerb theVerb)
	    (switch theVerb
	        (V_DO
				(if (<= (self distanceTo: gEgo) 15)
					(rm1701 setScript: getHen)
				else
					(gMessager say: N_ROOM 0 C_NOT_CLOSE_HEN 0)
				)
	        )
	        (else
	            (super doVerb: theVerb &rest)
	        )
	    )
	)
)

(instance getHen of Script
	(properties)

	(method (doit)
		(super doit:)
	)
	
	(method (changeState newState)
		(= state newState)
		(DebugPrint {getHen state: %d} state)
		(switch state
			(0
				(gGame handsOff:)
				(hen setMotion: NULL)
				(self cue:)
			)
			(1
				(gEgo view: ROSELLA_PEASANT_PICKUP_VIEW setLoop: 0 setCel: 0)
				(self cue:)
			)
			(2
				(gEgo setCycle: EndLoop self)
			)
			(3
				(hen dispose:)
				(gEgo get: INV_HEN)
				(gEgo setLoop: 1 setCel: 0)
				(self cue:)
			)
			(4
				(gEgo setCycle: EndLoop self)
			)
			(5
				(gGame handsOn:)
				(gEgo view: ROSELLA_PEASANT_VIEW setCycle: StopWalk -1)
				(rm1701 setScript: RoomScript)
			)
		)
	)
)

(instance ogreEatsRosella of Script
	(properties)
	
	

	(method (doit)
		(super doit:)
		(DebugPrint {ogreEatsRosella state: %d} (self state?))
		(DebugPrint {ogre cel: %d} (ogre cel?))
	)
	
	(method (changeState newState)
		(= state newState)
		(DebugPrint {ogreEatsRosella state: %d} state)
		(switch state
			(0
				(gEgo setMotion: NULL)
				(self cue:)
			)
			(1
				(gMessager say: N_ROOM 0 C_OGRE_EAT 0 self)
			)
			(2
				(gGame handsOff:)
				(gEgo hide:)
				(self cue:)
			)
			(3
				(ogre posn: 200 175 view: OGRE_EAT_ROSELLA_VIEW_A setSpeed: 30 setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(4
				(ogre view: OGRE_EAT_ROSELLA_VIEW_B setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(5
				(ogre view: OGRE_EAT_ROSELLA_VIEW_C setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(6
				(ogre view: OGRE_EAT_ROSELLA_VIEW_D setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(7
				(ogre view: OGRE_EAT_ROSELLA_VIEW_E setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(8
				(ogre view: OGRE_EAT_ROSELLA_VIEW_F setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(9
				(ogre setCycle: NULL)
				(DebugPrint {END})
			)
		)
	)
)




