;;; Sierra Script 1.0 - (do not remove this comment)
(script# CABIN_KEYHOLE_SCRIPT)
(include sci.sh)
(include game.sh)
(include 1705.shm)
(use Actor)
(use Cycle)
(use Door)
(use Feature)
(use Game)
(use Main)
(use Polygon)
(use System)

(public
	rm1705 0
)

(instance rm1705 of Room
	(properties
		noun N_ROOM
	)
	
	(method (init)
		(super init:)
		
		(self drawPic: gKeyholePic)
		(self setScript: RoomScript)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switchto state
			(
				(gGame handsOff:)
				(= seconds 3)
			)
			(
				(cond
					((== gKeyholePic CABIN_KEYHOLE_PIC_EMPTY)
						(= gCabinKeyholeState 0)
						(gMessager say: N_ROOM 0 C_EMPTY 0 self)
					)
					((== gKeyholePic CABIN_KEYHOLE_PIC_OGRE)
						(rm1705 setScript: henCinematic)
						
					)
					((== gKeyholePic CABIN_KEYHOLE_PIC_SLEEP)
						(= gCabinKeyholeState 2)
						(gMessager say: N_ROOM 0 C_OGRE_SLEEP 0 self)
						
					)
				)
			)
			(
				(gGame handsOn:)
				(gRoom newRoom: CABIN_CLOSET_SCRIPT)
			)
		)
	)
)



(instance henCinematic of Script
	(properties)
	
	(method (changeState newState)
		(= state newState)
		(switch state
			(0
				(= seconds 5)
			)
			(1
				(gMessager say: N_ROOM 0 C_OGRE 0 self)
			)
			(2
				(= seconds 2)
			)
			(3
				(= gKeyholePic CABIN_KEYHOLE_PIC_BOTH)
				(rm1705 drawPic: gKeyholePic)
				(self cue:)
			)
			(4
				(= seconds 2)
			)
			(5
				(gMessager say: N_ROOM 0 C_OGRE_HEN 0 self)
			)
			(6
				(= seconds 2)
			)
			(7
				(= gKeyholePic CABIN_KEYHOLE_PIC_SLEEP)
				(rm1705 drawPic: gKeyholePic)
				(self cue:)
			)
			(8
				(= seconds 2)
			)
			(9
				(gMessager say: N_ROOM 0 C_OGRE_SLEEP 0 self)
			)
			(10
				(= seconds 2)
			)
			(11
				(gGame handsOn:)
				(= gCabinKeyholeState 2)
				(gRoom newRoom: CABIN_CLOSET_SCRIPT)
			)
		)
	)
)
