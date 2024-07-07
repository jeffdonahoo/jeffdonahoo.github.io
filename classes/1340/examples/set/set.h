typedef int ElmtType;

class SetType
{
public: 
    SetType();
    SetType(int setSize);
    ~SetType();
    bool Insert(ElmtType newElmt);
    bool Delete(ElmtType newElmt);
	bool ElementOf(ElmtType newElmt);
	bool IsFull();
	void Print();

private:
	enum {DEFNOELMTS = 50};
	ElmtType* elements;
	int maxElements;
	int noElements;
};