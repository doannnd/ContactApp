package com.nguyendinhdoan.contactapp.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nguyendinhdoan.contactapp.R;
import com.nguyendinhdoan.contactapp.model.Contact;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private static final String TAG = ContactAdapter.class.getSimpleName();

    private Context context;
    private List<Contact> contactList;

    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: started.");
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_contact, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactViewHolder contactViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: started.");
        final Contact contact = contactList.get(position);

        // get data from contact model
        String contactName = contact.getName();
        String contactUrl = contact.getProfileImage();
        Log.d(TAG, "onBindViewHolder: contactName: " + contactName);
        Log.d(TAG, "onBindViewHolder: contactUrl: " + contactUrl);

        // show loading image
        contactViewHolder.loadingProgressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "onBindViewHolder: begin load image with picasso library");
        // load image
        Picasso.get()
                .load(contactUrl)
                .into(contactViewHolder.contactAvatarImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: load contact avatar success");
                        contactViewHolder.loadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: load contact avatar failed" + e.getMessage());
                        // hide loading
                        contactViewHolder.loadingProgressBar.setVisibility(View.GONE);
                    }
                });
        // set contact name
        contactViewHolder.contactNameTextView.setText(contactName);

        contactViewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onClick: clicked edit icon");
                ContactDetailFragment fragment = ContactDetailFragment.newInstance(contact);
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(context.getString(R.string.contact_detail_fragment));
                transaction.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView contactAvatarImageView;
        private TextView contactNameTextView;
        private ProgressBar loadingProgressBar;

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener =  onItemClickListener;
        }

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactAvatarImageView = itemView.findViewById(R.id.iv_contact_avatar);
            contactNameTextView = itemView.findViewById(R.id.tv_contact_name);
            loadingProgressBar = itemView.findViewById(R.id.pb_loading);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
