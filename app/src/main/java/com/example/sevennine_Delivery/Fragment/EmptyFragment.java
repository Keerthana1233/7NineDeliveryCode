package com.example.sevennine_Delivery.Fragment;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sevennine_Delivery.R;
import com.example.sevennine_Delivery.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class EmptyFragment extends Fragment {
    Fragment selectedFragment;
    LinearLayout backfeed,feedback_lay,main_layout,about_lay;
    TextView no_data,continue_text,toolbar_title;
    ImageView no_data_image;
    JSONObject lngObject;
    LinearLayout back_feed;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    SessionManager sessionManager;
    String description,status,message,fd_sucess,fd_failure,feeddesc;
    public static EmptyFragment newInstance() {
        EmptyFragment fragment = new EmptyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_layout, container, false);
        no_data_image=view.findViewById(R.id.no_dat_img);
        no_data=view.findViewById(R.id.no_data);
        continue_text=view.findViewById(R.id.continue_text);
        toolbar_title=view.findViewById(R.id.toolbar_title);
        back_feed=view.findViewById(R.id.back_feed);

        sessionManager=new SessionManager(getActivity());

       /* if (NewAddressDetails_Fragment.address==null){
            no_data_image.setVisibility(View.GONE);
            no_data.setText("No address is added");
        }else if (BankAccount_Fragment.bank_details==null){
            no_data_image.setVisibility(View.GONE);
            no_data.setText("No Bank detail is added");
        }
*/

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = AaSettingFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("emmpty");
                transaction.commit();

            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    selectedFragment = AaSettingFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.addToBackStack("emmpty");
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });

        try {

            lngObject = new JSONObject(sessionManager.getRegId("language"));

            System.out.println("llllllllllllkkkkkkkkkkkkkkk" + lngObject.getString("EnterPhoneNo"));


            toolbar_title.setText(lngObject.getString("SelectBankOffres").replace("\n",""));



        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        continue_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ( LandingPageActivity.loan_type_name) {
                    case "Crop Loan":
                        selectedFragment = CropLoanDetails.newInstance();
                        break;
                    case "Kisan Credit Card":
                        selectedFragment = KisanCreditCard.newInstance();
                        break;

                    case "Gold Loan":
                        selectedFragment = GoldLoan.newInstance();
                        break;
                    case "Asset Backed Loans":
                        selectedFragment = AssetsBackedLoan.newInstance();
                        break;
                    case "Agri.Term Loan":
                        selectedFragment = AgriTermLoan.newInstance();
                        break;
                    case "Agri. Over Draft":
                        selectedFragment = AgriOverDraft.newInstance();
                        break;

                    case "Produce Marketing":
                        selectedFragment = ProduceMarketingLoan.newInstance();
                        break;
                    case "Tractor Loan":
                        selectedFragment = LoanDetails1Fragment.newInstance();
                        break;
                    case "Farm Truck":
                        selectedFragment = FarmTruck.newInstance();
                        break;
                    case "Backhoe Loader":
                        selectedFragment = BackhoeLoader.newInstance();
                        break;
                    case "Commercial Vehicles":
                        selectedFragment = CommercialVehicles.newInstance();
                        break;
                    case "Combine Harvester":
                        selectedFragment = CombineHorvester.newInstance();
                        break;
                    case "Power Tiller":
                        selectedFragment = PowerTiller.newInstance();
                        break;
                    case "Farm Machinery":
                        selectedFragment = FarmMachinery.newInstance();
                        break;
                    case "Pump set":
                        selectedFragment = PumpSet.newInstance();
                        break;
                    case "Dairy loan":
                        selectedFragment = DairyLoan.newInstance();
                        break;
                    case "Poultry loan":
                        selectedFragment = PoultryLoan.newInstance();
                        break;
                    case "Goat Farm":
                        selectedFragment = GoatFram.newInstance();
                        break;
                    case "Fisheries loan":
                        selectedFragment = FisheriesLoan.newInstance();
                        break;
                    case "Bee keeping":
                        selectedFragment = BeeKeeping.newInstance();
                        break;
                    case "Sericulture loan":
                        selectedFragment = SericultureLoan.newInstance();
                        break;
                    case "Sheep & Goat rearing":
                        selectedFragment = SheepRearing.newInstance();
                        break;
                    case "Raising Cross-breeds":
                        selectedFragment = RaisingCross.newInstance();
                        break;
                    case "Horticulture loan":
                        selectedFragment = Horiculture.newInstance();
                        break;
                    case "Plantation loans":
                        selectedFragment = PlantationLoan.newInstance();
                        break;
                    case "Farm Foresty":
                        selectedFragment = FarmForesty.newInstance();
                        break;
                    case "Irrigation loans":
                        selectedFragment = IrrigationLoans.newInstance();
                        break;
                    case "Agri. Clinic":
                        selectedFragment = AgriCinic.newInstance();
                        break;
                    case "Agri. BusinessCenter":
                        selectedFragment = AgriBusinessCenters.newInstance();
                        break;
                    case "Land Purchase Scheme":
                        selectedFragment = LandPurchaseScheme.newInstance();
                        break;
                    case "Farm development Loan":
                        selectedFragment = FarmDevelopmentLoan.newInstance();
                        break;
                    case "Cold Storage Loan":
                        selectedFragment = ColdStorage.newInstance();
                        break;
                    case "Godown Loan":
                        selectedFragment = Godown.newInstance();
                        break;
                    case "Ware House Loan":
                        selectedFragment = Warehouse.newInstance();
                        break;
                    case "Agri Farm House Loan":
                        selectedFragment = AgriFarmHouse.newInstance();
                        break;
                    case "Self-Help Groups":
                        selectedFragment = SelfHelpGroups.newInstance();
                        break;
                    case "Micro savings":
                        selectedFragment = MicroSavings.newInstance();
                        break;
                    case "Micro finance":
                        selectedFragment = MicroFinance.newInstance();
                        break;
                    case "Lending to Micro Credit Groups(MCG)":
                        selectedFragment = LendingToMicroCredit.newInstance();
                        break;
                    case "Agriculture DRI loans":
                        selectedFragment = AgriculturalDRILoans.newInstance();
                        break;
                    case "Agri working capital":
                        selectedFragment = AgriWorkingCapital.newInstance();
                        break;
                    case "Mid cap credit":
                        selectedFragment = MidCapCredit.newInstance();
                        break;
                    case "Agri. Investments":
                        selectedFragment = AgriInvestment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack("bankkk22");
                transaction.commit();
            }
        });*/
        return view;
    }


    }
