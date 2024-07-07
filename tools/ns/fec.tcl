# 
# Author: Jeff Donahoo
# Date:   6.2.99
#
# Program to test FEC NS sender and receiver agents.  This program creates one of each.

# Allocate a simulator object
set ns [new Simulator]

# Allocate two nodes
set n0 [$ns node]
set n1 [$ns node]

# set up the NS and NAM trace files
set f [open out.tr w]
$ns trace-all $f
set nf [open out.nam w]
$ns namtrace-all $nf

# Create a 5Mb link with 2ms propagation delay between nodes 0 and 1
$ns duplex-link $n0 $n1 5Mb 2ms DropTail

# Create FEC Sender agent
set FECSnd [new Agent/FEC/Snd]
# Attach FEC Sender agent to node 0
$ns attach-agent $n0 $FECSnd
# Turn debugging on for FEC Sender agent
$FECSnd set debug_ true
# Have FEC Sender agent sent k+1 packets from each block
$FECSnd set extraPkts_ 1

# Create FEC Receiver agent
set FECRcv [new Agent/FEC/Rcv]
# Attach FEC Receiver agent to node 1
$ns attach-agent $n1 $FECRcv
# Enable debugging for FEC Receiver agent
$FECRcv set debug_ true

# Connect the FEC Sender to Receiver agent
$ns connect $FECSnd $FECRcv

# Sender starts listening for receiver request at 0.3 seconds
$ns at 0.3 "$FECSnd start 5 10 5"
# Receiver sends requrest to sender at 0.5 seconds
$ns at 0.5 "$FECRcv start 5 10 5" 

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

