
# E-Commerce Products App

I developed this project for show my knowledge in Android programming with Kotlin, MVVM design pattern, Firebase Realtime usage.

<p float="left">
<img src=figures/home.jpeg width="180">
<img src=figures/productDetail.jpeg width="180">
<img src=figures/categories.jpeg width="180">
<img src=figures/category-products.jpeg width="180">
<img src=figures/home-sort.jpeg width="180">
</p>

+ User can be register, login and logout.
+ The app lists products from Firebase Realtime Database and adds new products. 
+ Product details can be viewed, edited or deleted on relevent screens.
+ Products can be filtered with the categories screen also it can be sorted by price or date on the relevant screens.

___


I used the MVVM design pattern for separate Logic and Views (Fragments, Activities). 
+ LiveData and Callbacks were used for communication between view and view model.

___


Base classes was created for avoid repetitive code blocks and improve management. Base classes were created to prevent repetitive code blocks and improve management. Related classes inherit from the base class and have the required property. Base classes:
+ BaseActivity
+ BaseFragmemt
+ BaseViewModel

___


Database management is developed in FirebaseManegament class with using related Firebase methods inside each function. FirebaseUtils class was developed for convert data in DataSnapshot files from Firebase methods into custom models (eg. Product, User). 

Firebase Auth was used for user authorization, Realtime Database for Database and Firebase Storage to store product photo. Firebase Realtime Database and Storage structures:

<br/>
<img src=figures/database.PNG>

___

App was developed mostly using Android Jetpack Libraries (eg. ViewBinding, Navigation)

<br/>
<img src=figures/navigation.PNG>
 
 ___
 
Other Screen Shots:
<p float="left">
<img src=figures/addProduct.jpeg width="180">
<img src=figures/editProduct.jpeg width="180">
<img src=figures/loading.jpeg width="180">
<img src=figures/error.jpeg width="180">
<img src=figures/profile.jpeg width="180">
</p>


## License
[MIT](https://choosealicense.com/licenses/mit/)
