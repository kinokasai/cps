all:
	spin -a feu.pml
	gcc -DSAFETY -O2 pan.c -o pan
	./pan
	./pan -D > feu.dot
	dot -Tpng feu.dot -o lol.png > hmm.png