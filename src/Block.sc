;;; Sierra Script 1.0 - (do not remove this comment)
(script# 949)
(include sci.sh)
(use System)


;	
;	 This class defines a rectangular area that an Actor can't enter. In SCI1.1, this has mostly been
;	 superceded by Polygons.
;	
;	 Example usage::
;	
;	 	(theFrog observeBlocks: ((Blk new:)
;	 		left: 150
;	 		top: 140
;	 		right: 170
;	 		bottom: 160
;	 		yourself: )
;	 	)
(class Block of Object
	(properties
		name {Blk}
		top 0
		left 0
		bottom 0
		right 0
	)
	
	(method (doit param1)
		(return
			(if
				(or
					(<= (param1 brBottom?) top)
					(> (param1 brTop?) bottom)
					(< (param1 brRight?) left)
				)
			else
				(>= (param1 brLeft?) right)
			)
		)
	)
)

;	
;	 This class defines a rectangular area to which an Actor is constrained. In SCI1.1, this has mostly been
;	 superceded by Polygons.
;	
;	 Example usage::
;	
;	 	(theFrog observeBlocks: ((Cage new:)
;	 		left: 150
;	 		top: 140
;	 		right: 170
;	 		bottom: 160
;	 		yourself: )
;	 	)
(class Cage of Block
	(properties
		top 0
		left 0
		bottom 0
		right 0
	)
	
	(method (doit param1)
		(return
			(if
				(and
					(>= (param1 brTop?) top)
					(>= (param1 brLeft?) left)
					(<= (param1 brBottom?) bottom)
				)
				(<= (param1 brRight?) right)
			else
				0
			)
		)
	)
)
