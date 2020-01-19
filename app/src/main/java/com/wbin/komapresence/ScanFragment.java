package com.wbin.komapresence;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.ncorti.slidetoact.SlideToActView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private SlideToActView slider;
    private LinearLayout llManual;
    private boolean reverse = false;
    private Spinner spinnerForm;
    private Button btnScan;

    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        llManual = view.findViewById(R.id.ll_form_manual);
        llManual.setVisibility(View.GONE);
        slider = view.findViewById(R.id.slider_absen);
        spinnerForm = view.findViewById(R.id.spinner_form_manual);
        mCodeScanner = new CodeScanner(requireContext(), scannerView);
        btnScan = view.findViewById(R.id.btn_submit_form);

        checkPermission();

        slider.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                reverse = !reverse;
                slider.setReversed(reverse);

                if (reverse){
                    slider.setOuterColor(ContextCompat.getColor(requireContext(),R.color.bgChart));
                    slider.setInnerColor(ContextCompat.getColor(requireContext(),R.color.white));
                    slider.setIconColor(ContextCompat.getColor(requireContext(),R.color.textBlackPlan));
                    scannerView.setVisibility(View.GONE);
                    slider.setText("Slide to scan");
                    slider.setTextColor(ContextCompat.getColor(requireContext(),R.color.lightBlue));
                    llManual.setVisibility(View.VISIBLE);
                } else {
                    slider.setOuterColor(ContextCompat.getColor(requireContext(),R.color.lighterBlue));
                    slider.setInnerColor(ContextCompat.getColor(requireContext(),R.color.lightBlue));
                    slider.setIconColor(ContextCompat.getColor(requireContext(),R.color.white));
                    slider.setTextColor(ContextCompat.getColor(requireContext(),R.color.white));
                    scannerView.setVisibility(View.VISIBLE);
                    slider.setText("Slide to manual");
                    llManual.setVisibility(View.GONE);
                }

                slider.resetSlider();

            }
        });

        ArrayAdapter<CharSequence> adapterEvent = ArrayAdapter.createFromResource(requireContext(),
                R.array.event_name, R.layout.item_spinner_scan);
        adapterEvent.setDropDownViewResource(R.layout.item_spinner_dropdown_scan);
        spinnerForm.setAdapter(adapterEvent);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogAbsentFragment().show(requireFragmentManager(),"form");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCodeScanner.stopPreview();
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA,
                    }, 20);
        } else {
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length>0){
            if (requestCode == 20) {
                if (permissions[0].equals(Manifest.permission.CAMERA)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCodeScanner.startPreview();
                }
            }
        }

    }
}
