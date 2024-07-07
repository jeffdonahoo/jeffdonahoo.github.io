//***************************************
//Class:           ArrayType            
//Author:          your name             
//                                       
//Course:          CSI 1340              
//Instructor:      Donahoo               
//                                       
//Last modified:   date                  
//                                       
//***************************************
//This class implements an array.
//***************************************

class ArrayType 
{
public:
	//***************************************
	//Function:         Initialize        
	//Params:                                 
	//  None                
	//                                       
	//Return:  None                       
	//                                       
	//***************************************
	//This function initializes m_lastIndex = -1
	//and m_defaultValue = INITVALUE
	//***************************************
	void Initialize();
	//***************************************
	//Function:         Initialize        
	//Params:                                 
	//  initValue (in) - Value to use in array initialization                
	//                                       
	//Return:  None                       
	//                                       
	//***************************************
	//This function initializes m_lastIndex = -1 
	//and m_defaultValue = initValue
	//***************************************
	void Initialize(int initValue);

	//***************************************
	//Function:         Set        
	//Params:                                 
	//  index (in) - Index location to set value
	//  value (in) - Value to set location to
	//                                       
	//Return:  True if successful; false otherwise                      
	//                                       
	//***************************************
	//If the index is valid, this function sets array location index to 
	//value, initializes all array elements between m_lastIndex and index, 
	//and, if necessary, updates lastIndex
	//***************************************
	bool Set(int index, int value);

	//***************************************
	//Function:         Get        
	//Params:                                 
	//  index (in) - Index location to get value from
	//  value (out) - Variable to return value
	//                                       
	//Return:  True if successful; false otherwise                      
	//                                       
	//***************************************
	//If the index is valid, this function gets the value at array location index.
	//***************************************	
	bool Get(int index, int& value) const;

	//***************************************
	//Function:         GetLastIndex        
	//Params:   None                   
	//                                       
	//Return:  Location of last valid index                     
	//                                       
	//***************************************
	//This function gets the location of the last valid index (returns lastIndex).
	//***************************************
	int GetLastIndex() const;

	//***************************************
	//Function:         PrintArray        
	//Params:   None
	//                                       
	//Return:  None                      
	//                                       
	//***************************************
	//This function prints the valid array element values.
	//***************************************
	void PrintArray() const;

	//***************************************
	//Function:         CopyArray        
	//Params:   
	// source (in) - array to copy from
	//                                       
	//Return:  None                      
	//                                       
	//***************************************
	//This function copies all valid values of the source array 
	//and the element initialization value.
	//***************************************
	void CopyArray(const ArrayType& source);

private:
	enum {MAXSIZE = 100};      // Maximum array size
	enum {INITVALUE = 0};      // Default element value
	int m_array[MAXSIZE];      // Array
	int m_lastIndex;           // Largest index location being used in the array
	int m_initValue;           // Default initialization value of array element

	//***************************************
	//Function:         IndexOk        
	//Params:   
	// source (in) - array index to verify
	//                                       
	//Return:  True if valid; false otherwise                      
	//                                       
	//***************************************
	//This function verifies if the index is ok.
	//***************************************
	bool IndexOk(int index) const;
};
