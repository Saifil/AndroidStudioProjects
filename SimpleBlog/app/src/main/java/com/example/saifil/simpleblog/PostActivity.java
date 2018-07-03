package com.example.saifil.simpleblog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

public class PostActivity extends AppCompatActivity {

    private ImageButton mImageButton;
    private Uri mCropImageUri;
    private Uri mFinalImage = null; //holds the final image

    private Button mSubmitButton;
    private EditText mPostTitle;
    private EditText mPostDetails;
    private ProgressDialog mProgress;

    private FirebaseStorage mFirebaseStorage;
    private FirebaseDatabase mFirebaseDatabase;

    private DatabaseReference mBlogDatabseReference;
    private StorageReference mPhotoStorageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImageButton = findViewById(R.id.imgselect_id);
        mSubmitButton = findViewById(R.id.submit_btn_id);
        mPostTitle = findViewById(R.id.title_id);
        mPostDetails = findViewById(R.id.details_id);

        //create a ref to the firebase root storage path
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance(); //ref. to root of db

        //create a reference to the photo storage path
        mPhotoStorageReference = mFirebaseStorage.getReference().child("blog_images");
        //create a reference to the child of root called "blog"
        mBlogDatabseReference = mFirebaseDatabase.getReference().child("blog");

        mProgress = new ProgressDialog(this);
    }

    public void onImageButtonClicked(View view) {

        //call CropImage class function to select and crop the image
        CropImage.startPickImageActivity(this);

    }

    public void onPostSubmit(View view) {

        mProgress.setMessage("Posting to blog..");
        mProgress.show();

        final String title = mPostTitle.getText().toString().trim(); //trim removes leading and trailing spaces
        final String details = mPostDetails.getText().toString().trim();

        if (title.matches("") || details.matches("") || mFinalImage == null) {
            Toast.makeText(this, "Enter all fields to proceed", Toast.LENGTH_SHORT).show();
        }
        else {

            StorageReference photoRef = mPhotoStorageReference.child(mFinalImage.getLastPathSegment());
            photoRef.putFile(mFinalImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    //creates a child of "blog" with unique name
                    DatabaseReference mPostRef = mBlogDatabseReference.push();

                    //creates children to the newly added "blog"
                    mPostRef.child("title").setValue(title);
                    mPostRef.child("desc").setValue(details);
                    mPostRef.child("image").setValue(downloadUrl.toString());

                    mProgress.dismiss(); //dismiss the progress dialog

                    //redirect to main activity
                    Intent intent = new Intent(PostActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri imageUri = CropImage.getPickImageResultUri(this, data); //get the user selected image
            mFinalImage = imageUri;
            mImageButton.setImageURI(mFinalImage);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},   CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                mFinalImage = resultUri;
                mImageButton.setImageURI(mFinalImage);

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("TAG",error.getMessage());
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {

        //does the image cropping
        CropImage.activity(imageUri)
                .setInitialCropWindowPaddingRatio(0)
                .start(this);
    }
}
