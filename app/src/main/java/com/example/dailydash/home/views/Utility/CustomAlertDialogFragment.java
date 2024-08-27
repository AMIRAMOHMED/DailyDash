package com.example.dailydash.home.views.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.dailydash.R;

public class CustomAlertDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_IMAGE_RESOURCE = "image_resource";
    private static final String ARG_POSITIVE_BUTTON_TEXT = "positive_button_text";
    private static final String ARG_NEGATIVE_BUTTON_TEXT = "negative_button_text";

    private String message;
    private int imageResource; // Resource ID for the image
    private String positiveButtonText;
    private String negativeButtonText;
    private CustomAlertDialogListener listener;

    public interface CustomAlertDialogListener {
        void onPositiveButtonClick();
        void onNegativeButtonClick();
    }

    public static CustomAlertDialogFragment newInstance(String message, int imageResource, String positiveButtonText, @Nullable String negativeButtonText) {
        CustomAlertDialogFragment fragment = new CustomAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putInt(ARG_IMAGE_RESOURCE, imageResource);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof CustomAlertDialogListener) {
            listener = (CustomAlertDialogListener) parentFragment;
        } else if (context instanceof CustomAlertDialogListener) {
            listener = (CustomAlertDialogListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement CustomAlertDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_alert_dialog, container, false);

        TextView messageTextView = view.findViewById(R.id.alert_message);
        Button positiveButton = view.findViewById(R.id.positive_button);
        Button negativeButton = view.findViewById(R.id.negative_button);
        ImageView imageView = view.findViewById(R.id.imageView12);

        if (getArguments() != null) {
            message = getArguments().getString(ARG_MESSAGE);
            imageResource = getArguments().getInt(ARG_IMAGE_RESOURCE);
            positiveButtonText = getArguments().getString(ARG_POSITIVE_BUTTON_TEXT);
            negativeButtonText = getArguments().getString(ARG_NEGATIVE_BUTTON_TEXT);
        }

        messageTextView.setText(message);

        // Set the image resource to the ImageView
        imageView.setImageResource(imageResource);

        positiveButton.setText(positiveButtonText);
        if (negativeButtonText != null) {
            negativeButton.setText(negativeButtonText);
            negativeButton.setVisibility(View.VISIBLE);
        } else {
            negativeButton.setVisibility(View.GONE);
        }

        positiveButton.setOnClickListener(v -> {
            listener.onPositiveButtonClick();
            dismiss();
        });

        negativeButton.setOnClickListener(v -> {
            listener.onNegativeButtonClick();
            dismiss();
        });

        return view;
    }
}