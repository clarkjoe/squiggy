;;; Sierra Script 1.0 - (do not remove this comment)
(script# INSET_SCRIPT)
(include sci.sh)
(include game.sh)
(use Main)
(use Actor)
(use System)


;	
;	 An Inset lets you create a small window on the screen which can contain its own
;	 cast, and respond to its own events and such.
(class Inset of Code
	(properties
		picture 0
		anOverlay 0
		style $0064
		view 0
		loop 0
		cel 0
		x 0
		y 0
		priority 14
		register 0
		hideTheCast 0
		caller 0
		owner 0
		script 0
		oldCast 0
		oldFeatures 0
		oldATPs 0
		oldMH 0
		oldKH 0
		oldDH 0
		oldWH 0
		oldObstacles 0
		oldStyle 0
		inset 0
		disposeNotOnMe 0
		modNum -1
		noun 0
		insetView 0
	)
	
	;	
	;	 Initializes the Inset. Generally you would override this to initialize your Inset
	;	 with the necessary Props and such. Then call (super:init(rest params)).
	;	
	;	 :param heapPtr theCaller: Object that gets cue()'d when the Inset is disposed.
	;	 :param heapPtr theOwner: Generally a room.
	;	
	(method (init theCaller theOwner theRegister)
		(= owner theOwner)
		(owner inset: self)
		(= register theRegister)
		(= caller theCaller)
		(if hideTheCast (self hideCast: 1))
		(= oldCast gCast)
		(= oldFeatures gFeatures)
		(= oldATPs gAddToPics)
		(= oldMH gOldMH)
		(= oldKH gOldKH)
		(= oldDH gOldDH)
		(= oldWH gWalkHandler)
		(= oldObstacles (gRoom obstacles?))
		(gRoom obstacles: ((List new:) add: yourself:))
		(= gCast (EventHandler new:))
		(gCast name: {newCast} add:)
		(= gFeatures (EventHandler new:))
		(gFeatures name: {newFeatures} add: self)
		(= gAddToPics (EventHandler new:))
		(gAddToPics name: {newATPs} add:)
		(= gOldMH (EventHandler new:))
		(gOldMH name: {newMH} add: self)
		(= gOldKH (EventHandler new:))
		(gOldKH name: {newKH} add: self)
		(= gOldDH (EventHandler new:))
		(gOldDH name: {newDH} add: self)
		(= gWalkHandler (EventHandler new:))
		(gWalkHandler name: {newWH} add:)
		(gTheDoits add: self)
		(self drawInset:)
	)
	
	(method (doit)
		(if script (script doit:))
		(if (not hideTheCast) (Animate (oldCast elements?) 0))
	)
	
	(method (dispose param1 &tmp theCaller)
		(gFeatures delete: self)
		(gOldMH delete: self)
		(gOldKH delete: self)
		(gOldDH delete: self)
		(gWalkHandler delete: self)
		(gTheDoits delete: self)
		(if inset (inset dispose: 0))
		(gCast
			eachElementDo: #dispose
			eachElementDo: #delete
			release:
			dispose:
		)
		(gAddToPics dispose:)
		(gFeatures dispose:)
		(gOldMH dispose:)
		(gOldKH dispose:)
		(gOldDH dispose:)
		(gWalkHandler dispose:)
		((gRoom obstacles?) dispose:)
		(owner inset: 0)
		(if (or (not argc) param1) (self refresh:))
		(if (or (not argc) param1)
			(= gAddToPics oldATPs)
			(gAddToPics doit:)
		)
		(gRoom obstacles: oldObstacles)
		(= gCast oldCast)
		(= gFeatures oldFeatures)
		(= gOldMH oldMH)
		(= gOldKH oldKH)
		(= gOldDH oldDH)
		(= gWalkHandler oldWH)
		(if hideTheCast (self hideCast: 0))
		(if (and (or (not argc) param1) caller)
			(= theCaller caller)
			(= caller 0)
			(theCaller cue:)
		)
	)
	
	(method (setScript theScript)
		(if (IsObject script) (script dispose:))
		(= script (if argc theScript else 0))
		(if script (script init: self &rest))
	)
	
	(method (handleEvent pEvent &tmp [temp0 2])
		(return
			(cond 
				((and inset (inset handleEvent: pEvent)) 0)
				((& (pEvent type?) evVERB)
					(cond 
						((self onMe: pEvent) (pEvent claimed: TRUE) (self doVerb: (pEvent message?)))
						(disposeNotOnMe (pEvent claimed: TRUE) (self dispose:))
						(else (return 0))
					)
				)
			)
		)
	)
	
	(method (doVerb theVerb)
		(if (== modNum -1) (= modNum gRoomNumber))
		(if
			(and
				gMessageType
				(Message msgGET modNum noun theVerb 0 1)
			)
			(gMessager say: noun theVerb 0 0 0 modNum)
		)
	)
	
	(method (hideCast param1 &tmp temp0 temp1)
		(= temp0 0)
		(= temp1 (if param1 1000 else -1000))
		(while (< temp0 (gCast size?))
			((gCast at: temp0)
				z: (+ ((gCast at: temp0) z?) temp1)
			)
			(++ temp0)
		)
		(Animate (gCast elements?) 0)
	)
	
	(method (drawInset)
		(if (> picture 0)
			(DrawPic
				picture
				(if anOverlay 100 else style)
				(if anOverlay 0 else 1)
			)
		)
		(if view
			(= insetView
				((inView new:)
					view: view
					loop: loop
					cel: cel
					x: x
					y: y
					setPri: priority
					ignoreActors: 1
					init:
					yourself:
				)
			)
		)
	)
	
	(method (restore)
		(self drawInset:)
		(if inset ((inset oldATPs?) doit:) (inset restore:))
	)
	
	(method (refresh)
		(if view
			(DrawPic (gRoom picture?) 100)
		else
			(DrawPic (gRoom picture?) style)
		)
		(gRoom style: oldStyle)
		(if (!= gPicNumber -1)
			(DrawPic gPicNumber 100 dpNO_CLEAR)
		)
		(if (gRoom inset:) ((gRoom inset:) restore:))
	)
	
	;
	; .. function:: setInset([theInset theCaller theRegister])
	;
	; 	Sets an :class:`Inset` on this Inset! To clear the inset, pass no parameters.
	;
	; 	:param heapPtr theInset: The Inset instance.
	; 	:param heapPtr theCaller: An object that will get cue()'d when the Inset is disposed.
	;
	; 	Example usage::
	;
	; 		(send myInset:setInset(anotherSubInset))
	;
	(method (setInset theInset theCaller theRegister)
		(if inset (inset dispose:))
		(if (and argc theInset)
			(theInset
				init:
					(if (>= argc 2) theCaller else 0)
					self
					(if (>= argc 3) theRegister else 0)
			)
		)
	)
	
	;
	; .. function:: onMe(theObj)
	;
	; .. function:: onMe(x y)
	;
	; If the Inset has a view, return true if the object is on that view.
	;
	; 	:param heapPtr theObj: An object with x and y properties.
	; 	:param number x: The x coordinate.
	; 	:param number y: The y coordinate.
	; 	:returns: TRUE if the object is on the Inset's view. If there is no view, returns TRUE.
	;
	(method (onMe param1 param2 &tmp temp0 temp1)
		(if (IsObject param1)
			(= temp0 (param1 x?))
			(= temp1 (param1 y?))
		else
			(= temp0 param1)
			(= temp1 param2)
		)
		(return
			(if view
				(return (insetView onMe: param1 param2))
			else
				(return TRUE)
			)
		)
	)
)

(instance inView of View
	(properties)
	
	(method (handleEvent)
		(return 0)
	)
)
