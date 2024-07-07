# 
# Author: Jeff Donahoo
# Date:   6.2.99
#
# Program to test FEC NS sender and receiver agents with multicast.

# Allocate a simulator object with multicast enabled
set ns [new Simulator -multicast on]

# Allocate two nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

$ns color 1 red
# prune/graft packets
$ns color 30 purple
$ns color 31 blue

# set up the NS and NAM trace files
set f [open out.tr w]
$ns trace-all $f
set nf [open out.nam w]
$ns namtrace-all $nf

# Create a 5Mb link with 2ms propagation delay between nodes 0 and 1
$ns duplex-link $n0 $n1 5Mb 2ms DropTail
$ns duplex-link $n0 $n2 5Mb 2ms DropTail

$ns duplex-link-op $n0 $n1 orient right-up
$ns duplex-link-op $n0 $n2 orient right-down

# Select type of multicast routing
set mproto DM
set mrthandle [$ns mrtproto $mproto {}]
set mcgroup [Node allocaddr]

# Create FEC Sender agent
set FECSnd [new Agent/FEC/Snd]
# Attach FEC Sender agent to node 0
$ns attach-agent $n0 $FECSnd
# Turn debugging on for FEC Sender agent
$FECSnd set debug_ true
# Have FEC Sender agent sent k+1 packets from each block
$FECSnd set extraPkts_ 1

# Create FEC Receiver agent
set FECRcv1 [new Agent/FEC/Rcv]
# Attach FEC Receiver agent to node 1
$ns attach-agent $n1 $FECRcv1
# Enable debugging for FEC Receiver agent
$FECRcv1 set debug_ true
$ns connect $FECRcv1 $FECSnd

# Create FEC Receiver agent
set FECRcv2 [new Agent/FEC/Rcv]
# Attach FEC Receiver agent to node 2
$ns attach-agent $n2 $FECRcv2
# Enable debugging for FEC Receiver agent
$FECRcv2 set debug_ true
$ns connect $FECRcv2 $FECSnd

$FECSnd set dst_addr_ $mcgroup
$FECSnd set dst_port_ 0
$FECSnd set class_ 1

$ns at 0.4 "$n1 join-group $FECRcv1 $mcgroup"
$ns at 0.4 "$n2 join-group $FECRcv2 $mcgroup"

# Sender starts listening for receiver request at 0.3 seconds
$ns at 0.3 "$FECSnd start 5 10 5"
# Receiver sends request to sender at 0.5 seconds
$ns at 0.5 "$FECRcv1 start 5 10 5"
$ns at 0.5 "$FECRcv2 start 5 10 5"

# Procedure to finish processing and start nam
proc finish {} {

    # Gain access to global variable for trace files
    global ns f nf
    # Flush and close trace files
    $ns flush-trace
    close $f
    close $nf
    
    # Execute nam on trace file
    puts "running nam..."
    exec nam out.nam &
    exit 0
}

# Execute the NS simulation
$ns run
# After the NS simulation completes, run the finish procedure
finish

