#include <iostream>
#include "pqueue.h"

using namespace std;

struct JobType
{
    int jobID;        // Identifier for job
    int submitTime;   // Time job submitted to queue
    int execTime;     // Time to execute job after it gets the processor
};

void main()
{
    QueType<JobType> jobQ;

    int nextDQTime = -1;  // Time to try next job dequeue
    JobType job;          // Current job
    int nextJobID = 0;    // ID for next job
    int executingJobID;   // ID of currently executing job

    for (int time = 1; time <= 20; time++)  // time is the current simulation time (in secs.)
    {
        cout << "Time: " << time << endl;

        if ((rand() % 2) == 1) // 50% chance of new job
        {
            // Schedule new job
            job.jobID = nextJobID;
            job.submitTime = time;
            job.execTime = (rand() % 4) + 1;  // Execution time between 1 and 4 inclusive
            jobQ.Enqueue(job, 4 - job.execTime);  // Higher priority to shorter jobs
            cout << "    Submitted job " << job.jobID << " with an execution time of " << 
                job.execTime << " secs." <<endl;
            nextJobID++;    // Increment job ID for next job
        }

        if (time == nextDQTime)
        {
            cout << "    Done with job " << executingJobID << endl;
        }

        if (time >= nextDQTime)
        {
            if (jobQ.Dequeue(job))
            {
                cout << "    Executing job " << job.jobID << " for " << job.execTime << 
                    " secs after waiting " << time - job.submitTime <<" secs." << endl;
                nextDQTime = time + job.execTime;
                executingJobID = job.jobID;
            }
        }
    }
}
