;;; Sierra Script 1.0 - (do not remove this comment)
;
; * SCI Game Header
; *************************************************************************
; * Put all the defines specific to your game in here
(include Verbs.sh)
(include Talkers.sh)

; non-room scripts
(define MAIN_SCRIPT 0)
(define ROSELLA_SCRIPT 1)
(define INGAME_DEBUG_SCRIPT 10)
(define DISPOSECODE_SCRIPT 11)
(define COLORINIT_SCRIPT 12)
(define ABOUT_SCRIPT 13)
(define DEBUGOUT_SCRIPT 14)
(define INVENTORY_SCRIPT 15)
(define DEBUGROOM_SCRIPT 16)
(define DEATH_SCRIPT 20)
(define PRITALKER_SCRIPT 22)
(define GAMECONTROLS_SCRIPT 24)
(define CHOICETALKER_SCRIPT 30)
(define SPEAKWINDOW_SCRIPT 877)
(define INSET_SCRIPT 923)
(define CONVERSATION_SCRIPT 925)
(define FLIPPOLY_SCRIPT 926)
(define PATHAVOIDER_SCRIPT 927)
(define PATHFOLLOW_SRIPT 932)
(define SLIDER_SCRIPT 934)
(define OSCILLATE_SCRIPT 939)
(define MOVECYCLE_SCRIPT 942)
(define POLYGONEDIT_SCRIPT 943)
(define DIALOGEDIT_SCRIPT 947)
(define FEATUREWRITER_SCRIPT 948)
(define MOVEFORWARD_SCRIPT 951)
(define DOOR_SCRIPT 954)
(define FORWARDCOUNT_SCRIPT 956)
(define DISPOSELOAD_SCRIPT 958)
(define DIRECTPATH_SCRIPT 964)
(define REVERSECYCLE_SCRIPT 969)
(define SCALETO_SRIPT 975)
(define CONTROLSBASE_SCRIPT 978)
(define SAVERESTORE_SCRIPT 990)
(define FILE_SCRIPT 993)

; room scripts
(define RM_OGRE_LIVING_ROOM 1701)
(define RM_OGRE_KITCHEN 1702)
(define RM_OGRE_UPSTAIRS 1703)
(define RM_OGRE_CLOSET 1704)
(define RM_LOGO 9000)
(define RM_TITLE 9001)

; views
(define VIEW_ROSELLA_TALKER 1)
(define VIEW_ROMAN_NUMERAL_4 2)
(define VIEW_TITLE_BUTTONS 3)
(define VIEW_AXE 900)

; loops
(define LOOP_EYE 0)
(define LOOP_MOUTH 1)
(define LOOP_RESTORE_BUTTON 0)
(define LOOP_OPENING_BUTTON 1)
(define LOOP_HELP_BUTTON 2)
(define LOOP_PLAY_BUTTON 3)

; cel numbers
(define CEL_ON_HOVER_BUTTON 0)
(define CEL_OFF_HOVER_BUTTON 1)

; audio resources
(define SONG_TITLE_SCREEN 0)

; inventory items
(define INV_AXE 0)

; Indices for the icons in the icon bar
(define ICONINDEX_WALK 0)
(define ICONINDEX_LOOK 1)
(define ICONINDEX_DO 2)
(define ICONINDEX_TALK 3)
(define ICONINDEX_CUSTOM 4)
(define ICONINDEX_CURITEM 5)
(define ICONINDEX_INVENTORY 6)
(define ICONINDEX_SETTINGS 7)
(define ICONINDEX_HELP 8)
