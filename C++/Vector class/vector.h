
#ifndef VECTOR_H
#define VECTOR_H
#include <ostream>
#include <initializer_list>
#include <stdexcept>
#include <cstddef> 

template<typename T>
class Vector{

public:
	class ConstIterator;
	class Iterator;
	using value_type= T;
	using size_type=std::size_t;
	using difference_type=std::ptrdiff_t;
	using reference=value_type&;
	using const_reference= const value_type&;
	using pointer=value_type*;
	using const_pointer=const value_type*;
	using iterator=Vector::Iterator;
	using const_iterator=Vector::ConstIterator;
	
private:
	size_type sz;
	size_type max_sz;
	pointer values;

//#########################Iterator########################################################################################
public:
class Iterator{

public:
	friend class Vector;
	friend class ConstIterator;
	using value_type = Vector::value_type;
	using reference = Vector::reference;
	using pointer = Vector::pointer;
	using difference_type = Vector::difference_type;
	using iterator_category = std::forward_iterator_tag;
	
private:
	pointer ptr;
	pointer is_end; //damit iterator nicht out of range sein kann
	
public:
	Iterator():ptr{nullptr},is_end{nullptr}{};
	Iterator(pointer ptr):ptr{ptr},is_end{ptr}{};// wenn It nicht über vector sonder direkt erzeugt wird kann es zu problemen führen
	Iterator(pointer ptr, pointer is_end):ptr{ptr},is_end{is_end}{};
	
	//member impl
	reference operator*()const{
		if(ptr>= is_end) throw std::runtime_error("It out of range");
		return *ptr;
	}
//--------------------------------------
	pointer operator->()const{
		if(ptr>= is_end) throw std::runtime_error("It out of range");
		return ptr;
	}
//--------------------------------------	
	 bool operator==(const const_iterator& other) const{
		return this->ptr==other.ptr;
	}	
//--------------------------------------
	 bool operator!=(const const_iterator& other)const {
		return this->ptr!=other.ptr;
	}

//--------------------------------------
	iterator& operator++(){
		if (ptr < is_end)
			++ptr;
		return *this;	
	}
//--------------------------------------	
	iterator operator++(int){
			iterator old_it_val=*this;
			if (ptr < is_end)
				++(*this);
			return old_it_val;
	}
//--------------------------------------
	operator const_iterator()const{
		return ConstIterator(ptr,is_end);
	}
//--------------------------------------
	

	
};

//############################Const Iterator#####################################################################################
class ConstIterator{

public:
	friend class Iterator;
	friend class Vector;
	using value_type = Vector::value_type;
	using reference = Vector::const_reference;
	using pointer = Vector:: const_pointer;
	using difference_type = Vector::difference_type;
	using iterator_category = std::forward_iterator_tag; 

private:
	pointer ptr;
	pointer is_end;

public:
	ConstIterator():ptr{nullptr},is_end{nullptr}{};
	ConstIterator(pointer ptr):ptr{ptr},is_end{ptr}{};
	ConstIterator(pointer ptr, pointer is_end):ptr{ptr},is_end{is_end}{};

//member impl
	reference operator*() const{
		if(ptr>= is_end) throw std::runtime_error("It out of range");
		return *ptr;
	}
//--------------------------------------
	pointer operator->() const{
		if(ptr>= is_end) throw std::runtime_error("It out of range");
		return ptr;
	}	
//--------------------------------------
	 bool operator==(const const_iterator& other) const{
			return this->ptr==other.ptr;
	}
//--------------------------------------
	 bool operator!=(const const_iterator& other) const{
		return this->ptr!=other.ptr;	
	}
//--------------------------------------
	const_iterator& operator++(){
		if (ptr < is_end)
			++ptr;
		return *this;
	}
//--------------------------------------
	const_iterator operator++(int) {
		const_iterator old_it_val=*this;
		if (ptr < is_end) 
			++(*this);
		return old_it_val;
		
	}
//--------------------------------------	
	 friend difference_type operator-(const ConstIterator& lop, const ConstIterator& rop);
	 
};


//##########################################VectorMethoden#######################################################################
public:
	Vector() : sz{0}, max_sz{0}, values{nullptr}{};
//--------------------------------------
	Vector(const Vector& other) : sz{other.sz}, max_sz{other.max_sz}, values{new value_type[other.max_sz]}{
		for(size_type i{0};i<sz;i++){
			values[i]=other.values[i];
		}
	}
//--------------------------------------
	Vector(size_type n):sz{0},max_sz{n},values{new value_type[n]}{};
//--------------------------------------
	Vector(std::initializer_list <value_type> init_list):sz{init_list.size()},max_sz{sz},values{new value_type[sz]}{
		size_type i{0};
		for(const auto& value : init_list){
			values[i++]= value;
		}
	}
//--------------------------------------
		
//--------------------------------------
	~Vector(){
		delete[] values;
	}
//--------------------------------------
	Vector& operator=(const Vector& other){
		if(this != &other){
			delete [] values;
			sz=other.sz;
			max_sz=other.max_sz;
			values = new value_type[other.max_sz];
			for(size_type i{0};i<sz;i++){
				values[i]=other.values[i];
			}
		
		}
		return *this;
	}
//-------------------------------------
	size_type size() const{
		return sz;
	}
//--------------------------------------
	bool empty() const{
  		return sz == 0;
	}
//--------------------------------------
	void clear(){
		sz=0;
	}
//--------------------------------------
	void reserve(size_type n){
		
		if(n>max_sz){
			pointer new_val= new value_type[n];
			for(size_type i{0};i<sz;++i)new_val[i]=values[i];
							
			delete[] values;
			values=new_val;
			max_sz=n;
		}			
	}
//--------------------------------------
	void shrink_to_fit(){
		if(max_sz!=sz && values!=nullptr){
			pointer new_val= new value_type[sz];
			for(size_type i{0};i<sz;i++)new_val[i]=values[i];
			delete[] values;
			values=new_val;
			max_sz=sz;
		}
	}
//--------------------------------------
	void push_back(value_type x){
		if(sz>=max_sz ){
			this->reserve( max_sz > 0 ? max_sz * 2 : 1);
		}
		values[sz]=x;
			++sz;
	}
//--------------------------------------

	void pop_back(){
		if(sz==0 || values==nullptr){
			throw std::runtime_error("Empty Vector");
		}
		--sz;
	
	}
//--------------------------------------
	reference operator[](size_type index){
		if(index>=sz || values==nullptr){
			throw std::runtime_error("Index out of bounce");
		}
		return values[index];
	}
//--------------------------------------
	const_reference operator[](size_type index)const{
		if(index>=sz || values==nullptr){
			throw std::runtime_error("Index out of bounce");
		}
		return values[index];
	}
//--------------------------------------
	size_type capacity() const{
		return max_sz;
	}
//--------------------------------------
	std::ostream &print(std::ostream &o) const{
		o<<"[";
		for(size_type i{0};i<sz;++i){
			if(i<sz-1)o<<values[i]<<", ";
			else o<<values[i];
		}
		return o<<"]";
	return o;
	}
//--------------------------------------		
	iterator begin(){			//hier wird IT mit korrektem is_end wert erzeugt
		return iterator{values,values +sz};
	}
//--------------------------------------
	 iterator end(){			//hier wird IT mit korrektem is_end wert erzeugt
		return iterator{values+sz,values +sz};
	}
//--------------------------------------
	const_iterator begin()const{ 		//hier wird IT mit korrektem is_end wert erzeugt
		return const_iterator{values,values +sz};
	
	}
	//--------------------------------------
	 const_iterator end()const{		//hier wird IT mit korrektem is_end wert erzeugt
		return const_iterator{values+sz,values +sz};
	}
	
//-------------------------------------	
	iterator insert(const_iterator pos, const_reference val){
		auto diff = pos-begin();
		if(diff<0||static_cast<size_type>(diff)>sz)
			throw std::runtime_error("iterator out uf bounds");
		size_type current{static_cast<size_type>(diff)};
		if(sz>=max_sz)
			reserve(max_sz > 0 ? max_sz * 2 : 1);   // hier wird sichergestellt, wenn max_sz gleich null ist, dass trotzdem genug speicher reserviert wird
		for(auto i{sz};i-->current;)
			values[i+1]=values[i];
		values[current]=val;
		++sz;
		return iterator{values+current, values+sz};
	}
//--------------------------------------	
	iterator erase(const_iterator pos){
		auto diff=pos-begin();
		if(diff<0||static_cast<size_type>(diff)>=sz)
			throw std::runtime_error("iterator out of bounds");
		size_type current{static_cast<size_type>(diff)};
		for(auto i{current};i<sz-1;++i)
			values[i]= values[i+1];
		--sz;
		return iterator{values+current, values+sz}; 
	}
	
	
	friend typename Vector<T>::difference_type operator-(const typename Vector<T>::ConstIterator& lop, const typename Vector<T>::ConstIterator& rop){
		return lop.ptr-rop.ptr;
	}

	

};
//###############################################global
	

	template<typename T>
	std::ostream & operator<<(std::ostream &o, const Vector<T> &v){return v.print(o);};

#endif
