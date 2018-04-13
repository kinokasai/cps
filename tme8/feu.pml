mtype = {orange, green, red, blinking_orange}
mtype light = blinking_orange

chan qlight = [0] of {mtype}

proctype light_activation() {
  light = red
  do
  :: light == red -> light = green; qlight ! red; qlight ! green
  :: light == orange -> light = red; qlight ! orange; qlight ! green
  :: light == green -> light = orange; qlight ! green; qlight ! orange
  :: true -> goto blink
  od

  blink:
    if
    :: true -> light = blinking_orange; qlight ! blinking_orange; goto blink
    fi
}

proctype observer() {
  mtype prec
  mtype next
  do
  :: qlight ? prec; qlight ? next;
    if
    :: prec == red; next == orange -> assert(false)
    :: prec == green; next == red -> assert(false)
    :: prec == orange; next == green -> assert(false)
    :: prec == blinking_orange || next == blinking_orange -> goto end_
    fi
  od

  end_:
    do
    :: goto end_
    od
}

init {
  atomic {
    run light_activation();
    run observer();
  }
}