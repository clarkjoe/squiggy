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
(use Chase)
(use Jump)

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
				(self setScript: bulldogEngagesRosella)
				(gEgo posn: 44 145)
			)
		)
		(bulldog init: ignoreActors: FALSE setCycle: Forward)
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
				(ogre init: setCycle: Forward setSpeed: 60)
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

(instance rosellaThrowsBone of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(DebugPrint {state: %d} state)
		(switchto state
			(
				(bulldog setMotion: NULL)
				(gGame handsOff:)
				(self cue:)
			)
			(
				(bone posn: (+ (gEgo x?) 15) (- (gEgo y?) 25) init: setSpeed: 10 setMotion: JumpTo (- (bulldog x?) 20) (- (bulldog y?) 15) self)
				(bulldog setCycle: StopWalk -1)
			)
			(
				(bone dispose:)
				(gEgo put: INV_BONE)
				(self cue:)
			)
			(
				(bulldog view: BULLDOG_CATCH_BONE_VIEW setLoop: 1)
				(self cue:)
			)
			(
				(bulldog setCycle: EndLoop self)
			)
			(
				(bulldog view: BULLDOG_WALK_BONE_VIEW setCycle: StopWalk -1 setMotion: MoveTo 67 135 self)
			)
			(
				(bulldog view: BULLDOG_CHEW_VIEW setCycle: Forward)
				(gGame handsOn:)
				(rm1701 setScript: RoomScript)
			)
		)
	)
)

(instance bulldogEngagesRosella of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switchto state
			(
				(bulldog view: BULLDOG_BARK_VIEW setLoop: 1 setCycle: Forward)
				(= seconds 5)
			)
			(
				(bulldog view: BULLDOG_RUN_VIEW setCycle: StopWalk -1 setMotion: Chase gEgo 25 self)
			)
			(
				(gEgo dispose:)
			)
		)
	)
)

(instance getHen of Script
	(properties)
	
	(method (changeState newState &tmp newSpeed)
		(= state newState)
		(switchto state
			(
				(gGame handsOff:)
				(hen setMotion: NULL)
				(self cue:)
			)
			(
				(= newSpeed gGEgoMoveSpeed)
				(gEgo view: ROSELLA_PEASANT_PICKUP_VIEW setCel: 0 setSpeed: (+ gGEgoMoveSpeed 2))
				(if (<= (gEgo x?) (hen x?))
					(gEgo setLoop: 0)
				else
					(gEgo setLoop: 2)
				)
				(self cue:)
			)
			(
				(gEgo setCycle: EndLoop self)
			)
			(
				(hen dispose:)
				(gEgo get: INV_HEN setCel: 0 setLoop: (+ (gEgo loop?) 1))
				(self cue:)
			)
			(
				(gEgo setCycle: EndLoop self)
			)
			(
				(gEgo view: ROSELLA_PEASANT_VIEW setSpeed: (- gGEgoMoveSpeed 2))
				(self cue:)
			)
			(
				(SetUpEgo -1 2)
				(gEgo init:)
				(self cue:)
			)
			(
				(gGame handsOn:)
				(rm1701 setScript: RoomScript)
			)
		)
	)
)

(instance ogreEatsRosella of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switchto state
			(
				(gEgo setMotion: NULL)
				(self cue:)
			)
			(
				(gMessager say: N_ROOM 0 C_OGRE_EAT 0 self)
			)
			(
				(gGame handsOff:)
				(gEgo hide:)
				(self cue:)
			)
			(
				(ogre posn: 200 175 view: OGRE_EAT_ROSELLA_VIEW_A setCel: 0 setLoop: 0 setSpeed: (+ gGEgoMoveSpeed 2))
				(ogre setCycle: EndLoop self)
			)
			(
				(ogre view: OGRE_EAT_ROSELLA_VIEW_B setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(
				(ogre view: OGRE_EAT_ROSELLA_VIEW_C setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(
				(ogre view: OGRE_EAT_ROSELLA_VIEW_D setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(
				(ogre view: OGRE_EAT_ROSELLA_VIEW_E setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(
				(ogre view: OGRE_EAT_ROSELLA_VIEW_F setCel: 0 setLoop: 0)
				(ogre setCycle: EndLoop self)
			)
			(
				(ogre setCycle: NULL)
				(gGame newRoom: DEATH_SCRIPT)
			)
		)
	)
)



(instance bulldog of Actor
	(properties
		view BULLDOG_RUN_VIEW
		x 163
		y 143
		signal ignAct
		noun N_BULLDOG
	)

	(method (doVerb theVerb)
		(switch theVerb
			(V_BONE
				(rm1701 setScript: rosellaThrowsBone)
			)
			(else 
				(super doVerb: theVerb &rest)
			)
		)
	)
)

(instance bone of Actor
	(properties
		view BONE_VIEW
		loop INVENTORY_ITEM_GAME_LOOP
		noun N_BONE
		signal ignAct
		priority -1
	)
)





