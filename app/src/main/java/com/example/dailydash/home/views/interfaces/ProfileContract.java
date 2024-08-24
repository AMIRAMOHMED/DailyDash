package com.example.dailydash.home.views.interfaces;


public interface ProfileContract {
    interface View {
        void showBackupSuccess();
        void showBackupFailure(String message);
        void showRestoreSuccess();
        void showRestoreFailure(String message);
        void navigateToLogin();
    }

    interface Presenter {
        void onLogout();
        void onBackup();
        void onRestore();
    }
}