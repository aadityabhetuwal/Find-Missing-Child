package com.prithak.findmissingperson.Dashboard.Activities;
import androidx.annotation.NonNull;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.prithak.findmissingperson.ModelClasses.GigsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MassFireBaseInsert {
    public static final int REQUEST_IMAGE = 100;
    public static final int PICK_VEDIO_CODE = 3;
    private EditText mpname, mpheight, mpfathername, mpplace, mppermanentadress, mpcontactnumber, edtmpage;
    private Button saveBtn;
    private FirebaseAuth auth;
    CircleImageView mpimage;
    StorageReference storageReference, storageReference2;
    Uri imageUri, vedioUri;
    Uri downloadUri;
    String imgUrlStr, vedioUrlstr;

    MediaController mediaController;

    public void createGig(String fmpname, String fmpfathername, String fmpheight, String fmpage,
                          String fmpplace, String fmppermanentadre, String fmpcontactnumber,
                          String imgUrl) {
        /*.......................................*/
        if(!uploadImage(imgUrl)){
            System.out.println("Could not upload image");
            return;
        }

        auth = FirebaseAuth.getInstance();


        Long currentTime = System.currentTimeMillis(); //getting current time in millis
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTime);
        String showTime = String.format("%1$tI:%1$tM:%1$tS %1$Tp" + "\n", cal);
        Date now = new Date();
        long timestamp = now.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String dateStr = sdf.format(timestamp);
        String dateStamp = showTime + dateStr;
        /*.........................................*/
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("AllUsersAccount").child(userId).child("MissingPersonPosts");
        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("MissingUsers");
        final String pushKey = ref1.push().getKey();
        if (TextUtils.isEmpty(fmpname)) {
            mpname.setError("Empty");
        } else if (TextUtils.isEmpty(fmpfathername)) {
            mpfathername.setError("Empty");
        } else if (TextUtils.isEmpty(fmpheight)) {
            mpheight.setError("Empty");
        } else if (TextUtils.isEmpty(fmpage)) {
            edtmpage.setError("Empty");
        } else if (TextUtils.isEmpty(fmpplace)) {
            mpplace.setError("Empty");
        } else if (TextUtils.isEmpty(fmpcontactnumber)) {
            mpcontactnumber.setError("Empty");
        } else if (imgUrlStr == null) {
//            Toast.makeText( getApplicationContext(),"Please upload the Image",Toast.LENGTH_SHORT ).show();
        } else {
            // data is found
//            final ProgressDialog dialog = new ProgressDialog(this);
//            dialog.setMessage("Preparing...");
//            dialog.show();
//            /*..........*/
            final GigsData data = new GigsData(
                    pushKey, userId, "", dateStamp, fmpname, fmpfathername, fmpheight, fmpage, fmpplace,
                    fmppermanentadre, fmpcontactnumber, imgUrlStr, vedioUrlstr
            );

            ref1.child(pushKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        ref2.child(pushKey).setValue(data);
//                        dialog.dismiss();
//                        onBackPressed();
//                        Toast.makeText( Create_Mp_Case.this, "Case Successfully Created", Toast.LENGTH_SHORT).show();

                    } else {
//                        dialog.dismiss();
//                        Toast.makeText( Create_Mp_Case.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String getFileExtension(String fileName) {
        String extension = null;

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    private boolean uploadImage(String imageUrl) {
//        if (imageUri != null) {
//            final Dialog dialogImg = new ProgressDialog(this);
//            ((ProgressDialog) dialogImg).setMessage("Uploading ...");
//            dialogImg.setCancelable(false);
//            dialogImg.show();

        final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                + "." + getFileExtension(imageUrl));

        StorageTask<UploadTask.TaskSnapshot> uploadTask = fileReference.putFile(imageUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
//                        dialogImg.dismiss();
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
//                        dialogImg.dismiss();
                    downloadUri = task.getResult();
                    imgUrlStr = downloadUri.toString();

                    return;
                     /*   String id = auth.getCurrentUser().getUid();
                        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("AllUsersAccount").child(id).child("Profile_Info");
                        final DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Users_List").child(id);
                        final HashMap map = new HashMap();
                        map.put("imgUrl", imgUrlStr);

//                        ref.updateChildren(map);

                        ref2.updateChildren(map);
                        ref3.updateChildren(map);
                        dialogImg.dismiss();
                        Toast.makeText(Create_Fp_Case.this.getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
*/
                } else {
                    System.out.println(task.getException().getMessage());
//                        dialogImg.dismiss();
//                        Toast.makeText( Create_Fp_Case.this.getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Error");
//                    dialogImg.dismiss();
//                    Toast.makeText( Create_Fp_Case.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }

    public static void main(String[] args){

    }
}
