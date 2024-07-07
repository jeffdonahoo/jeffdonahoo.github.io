set ns [new Simulator]

$ns color 0 blue
$ns color 1 red
$ns color 2 white

set n0 [$ns node]
set n1 [$ns node]

set f [open out.tr w]
$ns trace-all $f
set nf [open out.nam w]
$ns namtrace-all $nf

$ns duplex-link $n0 $n1 5Mb 2ms DropTail

set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0

set lm [new Agent/LossMonitor]
$ns attach-agent $n1 $lm

set em [new ErrorModel]
$em unit pkt
$em set rate_ 0.1
$em drop-target [new Agent/Null]
[$ns set link_([$n0 id]:[$n1 id])] install-error $em

$ns connect $udp0 $lm

$ns at .1 "$cbr0 start"
$ns at 1 "$cbr0 stop"

$ns at 3.0 "finish"

proc finish {} {
	global ns f nf lm
	$ns flush-trace
	close $f
	close $nf

	puts "Packets Lost: " 
        puts [$lm set nlost_]
	puts "Next Packet Expected: " 
        puts [$lm set expected_]
	puts "running nam..."
	exec nam out.nam &
	exit 0
}

$ns run
