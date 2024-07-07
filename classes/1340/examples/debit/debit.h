class DebitCard 
{
public:
	DebitCard();
	DebitCard(int limit);
	~DebitCard();
	bool Debit(float payment);  // Debit account by payment.  Return false if insufficient funds
	bool Credit(float payment); // Credit account by payment.  Return false if exceeds card credit limit
	float Balance() const;      // Return current balance

private:
	float m_balance;	  // a real number that indicates the current balance on the card
	float m_creditLimit;  // a real number indicating the card credit limit
};