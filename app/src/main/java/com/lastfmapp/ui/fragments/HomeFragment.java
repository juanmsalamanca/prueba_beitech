package com.lastfmapp.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lastfmapp.R;
import com.lastfmapp.persistence.DAO.AlbumDAO;
import com.lastfmapp.persistence.DAO.TrackDAO;
import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.lastfmapp.lastFMService.LastFMService;
import com.lastfmapp.lastFMService.ResultServiceInterface;
import com.lastfmapp.persistence.DAO.ArtistDAO;
import com.lastfmapp.persistence.DTO.TrackDTO;
import com.lastfmapp.ui.MainActivity;
import com.lastfmapp.ui.adapters.AdapterEventInterface;
import com.lastfmapp.ui.adapters.AlbumAdapter;
import com.lastfmapp.ui.adapters.ArtistAdapter;
import com.lastfmapp.ui.adapters.TrackAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    private LastFMService lastFMService = LastFMService.getInstance();
    private ArtistAdapter artistAdapter;
    private AlbumAdapter albumAdapter;
    private TrackAdapter trackAdapter;
    private List<ArtistDTO> listArtist = null;
    private List<AlbumDTO> listAlbums = null;
    private List<TrackDTO> listTracks = null;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (view != null) {
            if ((ViewGroup)view.getParent() != null)
                ((ViewGroup)view.getParent()).removeView(view);
            //return view;
        } else {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            chargeData(view);
        }


        ImageButton btnFind = view.findViewById(R.id.btn_home_search);
        final EditText editText =  view.findViewById(R.id.ed_home_txtFind);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt = editText.getText().toString();
                if (!txt.isEmpty()) {
                    searchArtist(view, txt);
                } else {
                    chargeData(view);
                }


            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    private void searchArtist (final View view, final String txt) {
        final ArtistDAO artistDAO = new ArtistDAO(getContext());
        if (MainActivity.checkInternetAccess()) {
            lastFMService.getArtistSearch(txt, new ResultServiceInterface<ArtistDTO>() {
                @Override
                public void onResult(boolean state, List<ArtistDTO> list) {

                    for (ArtistDTO a : list) {
                        artistDAO.insert(a);
                    }

                    MainActivity.progressBar.setVisibility(View.INVISIBLE);
                    System.out.println(list);

                    if (listArtist == null) {
                        listArtist = new LinkedList<>();
                        listArtist.addAll(list);
                    } else {
                        listArtist.clear();
                        listArtist.addAll(list);
                    }

                    chargeArtistAdapter(view, listArtist);

                }

                @Override
                public void onWait() {
                    MainActivity.progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(getContext(), "Fuera de linea", Toast.LENGTH_SHORT).show();
            List list = artistDAO.findLikeName(txt);
            if (listArtist == null) {
                listArtist = new LinkedList<>();
                listArtist.addAll(list);
            } else {
                listArtist.clear();
                listArtist.addAll(list);
            }

            chargeArtistAdapter(view, listArtist);
        }


        if (listAlbums == null) {
            listAlbums = new LinkedList<>();
        } else {
            listAlbums.clear();
        }
        chargeAlbumAdapter(view, listAlbums);
        if (listTracks == null) {
            listTracks = new LinkedList<>();
        } else {
            listTracks.clear();
        }
        chargeTrackAdapter(view, listTracks);



    }



    private void chargeData (View view) {
        chargeArtist(view);



    }


    private void chargeArtist(final View view){
        final ArtistDAO artistDAO = new ArtistDAO(getContext());
        if (MainActivity.checkInternetAccess()) {
            System.out.println("internet ok");
            lastFMService.getChartGetTopArtists(new ResultServiceInterface<ArtistDTO>() {
                @Override
                public void onResult (boolean state, List<ArtistDTO> list) {
                    for (ArtistDTO a : list) {
                        artistDAO.insert(a);
                    }

                    if (listArtist == null) {
                        listArtist = new LinkedList<>();
                        listArtist.addAll(list);
                    } else {
                        listArtist.clear();
                        listArtist.addAll(list);
                    }


                    MainActivity.progressBar.setVisibility(View.INVISIBLE);
                    chargeArtistAdapter(view, listArtist);
                }
                @Override
                public void onWait() {
                    MainActivity.progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            System.out.println("no internet");
            Toast.makeText(getContext(), "Fuera de linea", Toast.LENGTH_SHORT).show();
            List list = artistDAO.findAll();
            if (listArtist == null) {
                listArtist = new LinkedList<>();
                listArtist.addAll(list);
            } else {
                listArtist.clear();
                listArtist.addAll(list);
            }

            chargeArtistAdapter(view, listArtist);


        }

        if (listAlbums == null) {
            listAlbums = new LinkedList<>();
        } else {
            listAlbums.clear();
        }
        chargeAlbumAdapter(view, listAlbums);
        if (listTracks == null) {
            listTracks = new LinkedList<>();
        } else {
            listTracks.clear();
        }
        chargeTrackAdapter(view, listTracks);
    }

    private void chargeAlbums(final View view, final String artistName, String mbid){
        final AlbumDAO albumDAO = new AlbumDAO(getContext());
        if (MainActivity.checkInternetAccess()) {
            System.out.println("internet ok");
            lastFMService.getArtistGetTopAlbums(artistName, null, new ResultServiceInterface<AlbumDTO>() {
                @Override
                public void onResult(boolean state, List<AlbumDTO> list) {

                    for (AlbumDTO album : list) {
                        albumDAO.insert(album);
                    }
                    MainActivity.progressBar.setVisibility(View.INVISIBLE);

                    if (listAlbums == null) {
                        listAlbums = new LinkedList<>();
                        listAlbums.addAll(list);
                    } else {
                        listAlbums.clear();
                        listAlbums.addAll(list);
                    }

                    chargeAlbumAdapter(view, listAlbums);
                }
                @Override
                public void onWait() {
                    MainActivity.progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            System.out.println("no internet");
            List list = albumDAO.findByArtistName(artistName);
            if (listAlbums == null) {
                listAlbums = new LinkedList<>();
                listAlbums.addAll(list);
            } else {
                listAlbums.clear();
                listAlbums.addAll(list);
            }
            chargeAlbumAdapter(view, listAlbums);
        }
    }

    private void chargeTracks (final View view, final String artistName, final String albumName) {
        final TrackDAO trackDAO = new TrackDAO(getContext());
        if (MainActivity.checkInternetAccess()) {
            lastFMService.getAlbumGetInfo(artistName, albumName, new ResultServiceInterface<TrackDTO>() {
                @Override
                public void onResult(boolean state, List<TrackDTO> list) {
                    MainActivity.progressBar.setVisibility(View.INVISIBLE);
                    for (TrackDTO trackDTO : list) {
                        trackDAO.insert(trackDTO);
                    }

                    if (listTracks == null) {
                        listTracks = new LinkedList<>();
                        listTracks.addAll(list);
                    } else {
                        listTracks.clear();
                        listTracks.addAll(list);
                    }
                    chargeTrackAdapter(view, listTracks);
                }
                @Override
                public void onWait() {
                    MainActivity.progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            System.out.println("no internet");
            List list = trackDAO.findByArtistAlbum(artistName, albumName);

            if (listTracks == null) {
                listTracks = new LinkedList<>();
                listTracks.addAll(list);
            } else {
                listTracks.clear();
                listTracks.addAll(list);
            }
            chargeTrackAdapter(view, listTracks);
        }
    }




    private void chargeArtistAdapter(final View view, List list){

        if (artistAdapter == null) {
            artistAdapter = new ArtistAdapter(list, getContext(), new AdapterEventInterface() {
                @Override
                public void onClickItem(String name) {
                    System.out.println("click " + name);
                    chargeAlbums(view, name, null);

                    if (listTracks == null) {
                        listTracks = new LinkedList<>();
                    } else {
                        listTracks.clear();
                    }
                    chargeTrackAdapter(view, listTracks);
                }
            });
        } else {
            artistAdapter.notifyDataSetChanged();
        }



        RecyclerView rv = view.findViewById(R.id.rv_topArtist);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(horizontalLayoutManager);
        //rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(artistAdapter);
    }

    private void chargeAlbumAdapter(final View view, final List<AlbumDTO> list){
        System.out.println("album list " + list);

        if (albumAdapter == null) {
            albumAdapter = new AlbumAdapter(list, getContext(), new AdapterEventInterface() {
                @Override
                public void onClickItem(String albumName) {
                    if (list.size() > 0){
                        chargeTracks(view, list.get(0).getArtistName(), albumName);
                    }

                }
            });
        } else {
            albumAdapter.notifyDataSetChanged();
        }

        RecyclerView rv = view.findViewById(R.id.rv_topAlbums);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(horizontalLayoutManager);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(albumAdapter);
    }

    private void chargeTrackAdapter(final View view, List list){
        System.out.println("tracks list " + list);

        if (trackAdapter == null) {
            trackAdapter = new TrackAdapter(list, getContext(), new AdapterEventInterface() {
                @Override
                public void onClickItem(String albumName) {

                }
            });
        } else {
            trackAdapter.notifyDataSetChanged();
        }

        RecyclerView rv = view.findViewById(R.id.rv_topTracks);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setNestedScrollingEnabled(false);
        rv.setAdapter(trackAdapter);

    }










    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
