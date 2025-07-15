#ifndef ADS_SET_H
#define ADS_SET_H

#include <functional>
#include <algorithm>
#include <iostream>
#include <stdexcept>

template <typename Key, size_t N=7 >
class ADS_set {
public:
  class Iterator;
  using value_type = Key;
  using key_type = Key;
  using reference = value_type &;
  using const_reference = const value_type &;
  using size_type = size_t;
  using difference_type = std::ptrdiff_t;
  using const_iterator = Iterator;
  using iterator = const_iterator;
  //using key_compare = std::less<key_type>;                         // B+-Tree
  using key_equal = std::equal_to<key_type>;                       // Hashing
  using hasher = std::hash<key_type>;                              // Hashing




private:
	
	struct List{
		key_type key;
		List* next;
	
		List(const key_type &key, List* next=nullptr):key{key}, next{next}{}
	};
	
	List** table;
	size_type table_size{N};
	size_type current_size{0};
	float max_lf{0.7};
	
	void add(const key_type &key);
	List* locate(const key_type &key, size_type &idx) const;
	size_type h(const key_type &key) const{return hasher{}(key)% table_size;}
	void reserve(size_type n);
	void rehash(size_type n);
	
public:

  	ADS_set():table{new List*[N]{}}, table_size{N}, current_size{}{}
  
  	ADS_set(std::initializer_list<key_type> ilist):ADS_set{}{insert(ilist.begin(),ilist.end());}
  
	ADS_set(const ADS_set &other):table(nullptr), table_size(other.table_size), current_size(0), max_lf(other.max_lf) {
    
	table = new List*[table_size] {};
	    
		for (size_type i = 0; i < other.table_size; ++i) {
			List* current = other.table[i];
			while (current) {
				add(current->key); 
				current = current->next;
			}
		}
	}
  template<typename InputIt> ADS_set(InputIt first, InputIt last):ADS_set{}{insert(first,last);}     // PH1
  

	~ADS_set(){//PH2	
		for (size_t i = 0; i < table_size; ++i) {
			List* current = table[i];
			while (current != nullptr) {
				List* toDelete = current;
				current = current->next;
				delete toDelete;  
			}
		}
		delete[] table;
	}

  ADS_set &operator=(const ADS_set &other){//PH2
  	ADS_set tmp{other};
  	swap(tmp);
  	return *this;
  }
  ADS_set &operator=(std::initializer_list<key_type>ilist){//PH2
  	ADS_set tmp{ilist};
  	swap(tmp);
  	return *this;
  }
  
  std::pair<iterator,bool> insert(const key_type &key){ //PH2
  	size_type idx;
  	List* node=locate(key,idx);
  	if(node)return {iterator{table, table_size, idx, node},false};
  	reserve(current_size+1);
  	add(key);
  	node=locate(key,idx);
  	return {iterator{table,table_size,idx,node},true};
  }
  void clear(){//PH2
  	ADS_set tmp;
  	swap(tmp);
  }
  
  size_type erase(const key_type &key){//PH2
  	size_type idx;
  	List* node =locate(key,idx);
  	
  	if(node){
  		List* help=table[idx];
  		List*prev=nullptr;
  		
	  	while(help){
	  		if(help==node){
	  			if(prev)prev->next=help->next;
	  			else table[idx]=help->next;
	  			
	  			delete help;
	  			--current_size;
	  			return 1;
	  		}
	  		prev=help;
		  	help=help->next;
		  	
	  	}
	  }
  	return 0;
  }
  void swap(ADS_set &other){//PH2
  	using std::swap;
  	swap(table,other.table);
  	swap(table_size, other.table_size);
  	swap(current_size, other.current_size);
  	swap(max_lf, other.max_lf);
  	}
  
  
  iterator find(const key_type &key) const{//PH2
  	size_type idx;
  	List* node=locate(key,idx);
  	if(node)return iterator{table, table_size, idx, node};
  	return end();
  }
  
  const_iterator begin() const{//PH2
  	 for (size_type i = 0; i < table_size; ++i) {
        if (table[i] != nullptr)return iterator(table, table_size, i, table[i]); 
      }
    return end(); 
}

  const_iterator end() const{return iterator{table,table_size,table_size,nullptr};};//PH2
  
  friend bool operator==(const ADS_set &lhs, const ADS_set &rhs){//PH2
   if (lhs.current_size != rhs.current_size) return false;
    for (const auto &key : lhs) {
        if (rhs.count(key) == 0) return false;
    }
    return true;
  }
  
  friend bool operator!=(const ADS_set &lhs, const ADS_set &rhs){//PH2
  	return !(lhs == rhs);
  }

  size_type size() const{ return current_size;}                                              // PH1
  bool empty() const{return current_size==0;}                                                  // PH1

  void insert(std::initializer_list<key_type> ilist){insert(ilist.begin(), ilist.end());}               // PH1
 

  template<typename InputIt> void insert(InputIt first, InputIt last); // PH1

	size_type count(const key_type &key) const{// PH1
		size_type idx; 
		return locate(key,idx)!= nullptr;
 	 }
 			                        
	void dump(std::ostream &o = std::cerr) const;

};	
	template <typename Key, size_t N> 
	void ADS_set<Key,N>::add(const key_type &key){
		size_type idx {h(key)};
		List* help=table[idx];
		
		while(help){
			if(key_equal{}(help->key, key)){
				return;
			}
			help= help->next;
		}
	
    		 table[idx]=new List(key, table[idx]);
    		 ++current_size;
	}
	template <typename Key, size_t N >
	typename ADS_set<Key, N>::List* ADS_set<Key,N>::locate(const key_type &key, size_type &idx) const{
		idx =h(key)%table_size;
		List* help = table[idx];
		if(!help)return nullptr;
		while(help){
			if(key_equal{}(help->key,key))return help;
			help=help->next;
		}
		return nullptr;
	}
	
	template <typename Key, size_t N >	
	template<typename InputIt> void ADS_set<Key,N>::insert(InputIt first, InputIt last){
		for (auto it = first; it != last; ++it) {
     	   if (count(*it) == 0) {  
     	       reserve(current_size + 1);  
     	       add(*it);  
     	   }
   		 }
	}

	template <typename Key, size_t N >	
	void ADS_set<Key,N>::dump(std::ostream &o) const{
		o<<"table_size = "<<table_size<<" , current_size= "<<current_size<<std::endl;
		for(size_type idx {0};idx<table_size;++idx){
			o<<idx<<": ";
			List* help= table[idx];
			while(help){
				o<<help->key<<" -> ";
				help=help->next;
			}
			o<<" nullptr"<< std::endl;
		}
	}
	
	template <typename Key, size_t N >	
	void ADS_set<Key,N>::reserve(size_type n){
		if(table_size*max_lf>=n)return;
			size_type new_table_size {table_size};
			while(new_table_size*max_lf<n)(new_table_size*=2);
			rehash(new_table_size);		
	}
	
	template <typename Key, size_t N >	
	void ADS_set<Key,N>::rehash(size_type n){
		size_type new_table_size{std::max(N, std::max(n, static_cast<size_type>(current_size / max_lf)))};
    		List** new_table {new List*[new_table_size] {}};

    		size_type new_current_size {0}; 

    		for (size_type idx {0}; idx < table_size; ++idx) {
			List* help = table[idx];
        		while (help) {
				size_type new_idx = hasher{}(help->key) % new_table_size;
            
            		List* check = new_table[new_idx];
            		bool exists = false;
           		while (check) {
					if (key_equal{}(check->key, help->key)) {
                   			 exists = true;
                   			 break;
                		}	
                		check = check->next;
            		}
           		if (!exists) {
               		 new_table[new_idx] = new List(help->key, new_table[new_idx]);
               		 ++new_current_size;
           		}
            		List* next = help->next;
            		delete help;  
            		help = next;
        	    }
    		}

		delete[] table;  
		table = new_table;
		table_size = new_table_size;
		current_size = new_current_size; 
	}

template <typename Key, size_t N>
void swap(ADS_set<Key,N> &lhs, ADS_set<Key,N> &rhs) { lhs.swap(rhs); }
	

template <typename Key, size_t N>
class ADS_set<Key,N>::Iterator {
public:
  using value_type = Key;
  using difference_type = std::ptrdiff_t;
  using reference = const value_type &;
  using pointer = const value_type *;
  using iterator_category = std::forward_iterator_tag;
  
private:
	List** table;
	size_type table_size;
	size_type bucket_idx;
	List* current_node;
	
public:
	Iterator() : table(nullptr), table_size(0), bucket_idx(0), current_node(nullptr) {}


  explicit Iterator(List** table,size_type table_size, size_type bucket_idx,List* current_node):table{table}, table_size{table_size}, bucket_idx{bucket_idx}, current_node{current_node} {
  	while (!current_node && bucket_idx < table_size) {
      	++bucket_idx;
      	if (bucket_idx < table_size)current_node = table[bucket_idx];
    }
  }
  
  reference operator*() const{
  	if(current_node)return current_node->key;
  	throw std::runtime_error("iterator operator* - Iterator out of range");
  }
  
  pointer operator->() const{
  	if(current_node)return &current_node->key;
  	throw std::runtime_error("iterator operator* - Iterator out of range");
  }
  
  Iterator &operator++(){
  	if(current_node) current_node = current_node->next;
  	
  	while(!current_node && ++bucket_idx<table_size){
  		current_node=table[bucket_idx];
  	}
  	
  	return *this;
  }
  
  Iterator operator++(int){
  	iterator old_it=*this;
  	++(*this);
  	return old_it;
  }
  
  friend bool operator==(const Iterator &lhs, const Iterator &rhs){
	return lhs.current_node == rhs.current_node && lhs.bucket_idx == rhs.bucket_idx;
  }
  
  friend bool operator!=(const Iterator &lhs, const Iterator &rhs){
  	  return !(lhs == rhs);
  }
};

#endif