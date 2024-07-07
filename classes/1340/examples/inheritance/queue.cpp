#include <cstddef>

template <class ItemType>
struct NodeType
{
    ItemType info;
    NodeType* next;
};

template <class ItemType>
QueType<ItemType>::QueType()
{
    qFront = NULL;
    qRear = NULL;
}

template <class ItemType>
void QueType<ItemType>::MakeEmpty()
// Post: Queue is empty; all elements have been deallocated.
{
    NodeType<ItemType>* tempPtr;

    while (qFront != NULL)
    {
        tempPtr = qFront;
        qFront = qFront->next;
        delete tempPtr;
    }
    qRear = NULL;
}

template <class ItemType>		// Class destructor.
QueType<ItemType>::~QueType()
{
    MakeEmpty();
}

template <class ItemType>
bool QueType<ItemType>::IsFull() const
// Returns true if there is no room for another ItemType on the free store;
// false otherwise.
{
    NodeType<ItemType>* ptr;
    ptr = new NodeType<ItemType>;
    if (ptr == NULL)
        return true;
    else
    {
        delete ptr;
        return false;
    }
}

template <class ItemType>
bool QueType<ItemType>::IsEmpty() const
// Returns true if there are no elements on the queue; false otherwise.
{
    return (qFront == NULL);
}

template <class ItemType>
void QueType<ItemType>::Enqueue(ItemType newItem)
// Adds newItem to the rear of the queue.
// Pre:  Queue has been initialized and is not full.
// Post: newItem is at rear of queue.

{
    NodeType<ItemType>* newNode;

    newNode = new NodeType<ItemType>;
    newNode->info = newItem;
    newNode->next = NULL;
    if (qRear == NULL)
        qFront = newNode;
    else
        qRear->next = newNode;
    qRear = newNode;
}

template <class ItemType>
void QueType<ItemType>::Dequeue(ItemType& item)
// Removes front item from the queue and returns it in item.
// Pre:  Queue has been initialized and is not empty.
// Post: Front element has been removed from queue.
//       item is a copy of removed element.
{
    NodeType<ItemType>* tempPtr;

    tempPtr = qFront;
    item = qFront->info;
    qFront = qFront->next;
    if (qFront == NULL)
        qRear = NULL;
    delete tempPtr;
}