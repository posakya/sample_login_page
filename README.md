# sample_login_page

/*

library to make a login page and save data in firebase. You have to include firebase mannually

*/

/*

dependency

*/


 implementation 'com.github.posakya:sample_login_page:1.6'
 
 
/* 
  also don't forget to add in build gradle 
*/
 
 

allprojects {

    repositories {
    
        ....
        maven { url 'https://www.jitpack.io' }
        
    }
    
}

In Activity use : 


 LoginController loginController;
 
 loginController = new LoginController(MainActivity.this);
 
        loginController.loadView();
     
In Fragment use :


 LoginController loginController;
 
 loginController = new LoginController(getActivity());
 
        loginController.loadView();
        
 To save data in firebase use :
 
 
 
 @Override
 
    protected void onResume() {
    
        super.onResume();

        if (!LoginClass.email.isEmpty()){

         Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();

         SharedPreferenceClasses sp = new SharedPreferenceClasses(MainActivity.this);
         sp.retrieveData();

        Location location = new Location(GpsCoordinate.lat,GpsCoordinate.lng,"imadol");
        final User user = new User();
        user.setDevice_id(Double.valueOf(SharedPreferenceClasses.DeviceId));
        user.setEmail(SharedPreferenceClasses.Email);
        user.setFirst_name(SharedPreferenceClasses.FirstName);
        user.setLast_name(SharedPreferenceClasses.LastName);
        user.setId(SharedPreferenceClasses.Id);
        user.setLocation(location);

        FirebaseDatabase.getInstance().getReference().child("User").setValue(user, new DatabaseReference.CompletionListener() {
            @SuppressLint("DefaultLocale")
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {

                if (error != null){
                    Toast.makeText(MainActivity.this, "Failed to save data!!", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(MainActivity.this, "Successfully saved data!!", Toast.LENGTH_SHORT).show();

                }
            }

        });

        }

    }       
