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
		
		(cond
			((== gKeyholePic CABIN_KEYHOLE_PIC_OGRE)
				(self drawPic: CABIN_KEYHOLE_PIC_OGRE)
				(= gKeyholePic CABIN_KEYHOLE_PIC_BOTH)
			)
			((== gKeyholePic CABIN_KEYHOLE_PIC_BOTH)
				(self drawPic: CABIN_KEYHOLE_PIC_BOTH)
				(= gKeyholePic CABIN_KEYHOLE_PIC_EMPTY)
			)
			(else
				(self drawPic: CABIN_KEYHOLE_PIC_EMPTY)
				(= gKeyholePic CABIN_KEYHOLE_PIC_OGRE)
			)
		)

		(self setScript: RoomScript)
	)
)

(instance RoomScript of Script
	(properties)
	
	(method (doit)
		(super doit:)
		; code executed each game cycle
	)
	
	(method (changeState newState)
		(= state newState)
		(switchto state
			(
				(gGame handsOff:)
				(= seconds 3)
			)
			(
				(gMessager say: N_ROOM 0 0 0 self)
			)
			(
				(= seconds 2)
			)
			(
				(gGame handsOn:)
				(gRoom newRoom: CABIN_CLOSET_SCRIPT)
			)
		)
	)
)