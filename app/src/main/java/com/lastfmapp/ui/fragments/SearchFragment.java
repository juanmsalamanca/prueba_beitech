package com.lastfmapp.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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
import com.lastfmapp.lastFMService.DTO.WebResponseDTO;
import com.lastfmapp.lastFMService.LastFMService;
import com.lastfmapp.lastFMService.ResultServiceInterface;
import com.lastfmapp.lastFMService.ServiceInterface;
import com.lastfmapp.persistence.DTO.AlbumDTO;
import com.lastfmapp.persistence.DTO.ArtistDTO;
import com.lastfmapp.persistence.DTO.TrackDTO;
import com.lastfmapp.ui.MainActivity;
import com.lastfmapp.ui.adapters.AdapterEventInterface;
import com.lastfmapp.ui.adapters.AlbumAdapter;
import com.lastfmapp.ui.adapters.ArtistAdapter;
import com.lastfmapp.ui.adapters.Track2Adapter;
import com.lastfmapp.ui.adapters.TrackAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
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
    private Track2Adapter trackAdapter;
    private List<ArtistDTO> listArtist = null;
    private List<AlbumDTO> listAlbums = null;
    private List<TrackDTO> listTracks = null;

    private final String ARTISTS_TAG = "ARTISTAS";
    private final String ALBUMS_TAG = "ALBUMES";
    private final String TRACKS_TAG = "CANCIONES";

    private RecyclerView rv_artists = null;
    private RecyclerView rv_albums = null;
    private RecyclerView rv_tracks = null;



    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
            view = inflater.inflate(R.layout.fragment_search, container, false);

            rv_artists = view.findViewById(R.id.rv_searchArtist);
            rv_albums = view.findViewById(R.id.rv_searchAlbums);
            rv_tracks = view.findViewById(R.id.rv_searchTracks);

            rv_artists.setVisibility(View.VISIBLE);
            rv_albums.setVisibility(View.INVISIBLE);
            rv_tracks.setVisibility(View.INVISIBLE);


            ImageButton btnFind = view.findViewById(R.id.btn_search);
            final EditText editText =  view.findViewById(R.id.ed_txtFind);

            btnFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    if (MainActivity.checkInternetAccess()) {
                        String txt = editText.getText().toString();
                        if (!txt.isEmpty()) {
                            searchArtist(view, txt);
                            searchAlbum(view, txt);
                            searchTracks(view, txt);
                        }

                    } else {
                        Toast.makeText(getContext(), "No disponible sin internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            TabLayout tabs = view.findViewById(R.id.tabs);
            tabs.addTab(tabs.newTab().setText(ARTISTS_TAG));
            tabs.addTab(tabs.newTab().setText(ALBUMS_TAG));
            tabs.addTab(tabs.newTab().setText(TRACKS_TAG));

            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getText().equals(ARTISTS_TAG)) {
                        rv_artists.setVisibility(View.VISIBLE);
                        rv_albums.setVisibility(View.INVISIBLE);
                        rv_tracks.setVisibility(View.INVISIBLE);

                    } else if (tab.getText().equals(ALBUMS_TAG)) {
                        //chargeAlbumAdapter(view, listAlbums);
                        rv_artists.setVisibility(View.INVISIBLE);
                        rv_albums.setVisibility(View.VISIBLE);
                        rv_tracks.setVisibility(View.INVISIBLE);

                    } else if (tab.getText().equals(TRACKS_TAG)) {
                        rv_artists.setVisibility(View.INVISIBLE);
                        rv_albums.setVisibility(View.INVISIBLE);
                        rv_tracks.setVisibility(View.VISIBLE);

                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }



        return view;
    }

    private void searchTracks (final View view, String track) {
        lastFMService.getTrackSearch(track, new ResultServiceInterface() {
            @Override
            public void onResult(boolean state, List list) {
                MainActivity.progressBar.setVisibility(View.INVISIBLE);
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
    }


    private void searchAlbum (final View view, String album) {
        lastFMService.getAlbumSearch(album, new ResultServiceInterface() {
            @Override
            public void onResult(boolean state, List list) {
                MainActivity.progressBar.setVisibility(View.INVISIBLE);
                System.out.println("search" +list);
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
    }

    private void searchArtist (final View view, final String txt) {
        lastFMService.getArtistSearch(txt, new ResultServiceInterface() {
            @Override
            public void onResult(boolean state, List list) {
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

    }

    private void chargeTrackAdapter (final View view, List list) {
        if (trackAdapter == null) {
            trackAdapter = new Track2Adapter(list, getContext(), new AdapterEventInterface() {
                @Override
                public void onClickItem(String name) {

                }
            });
        } else {
            trackAdapter.notifyDataSetChanged();
        }
        rv_tracks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_tracks.setAdapter(trackAdapter);
        rv_tracks.setNestedScrollingEnabled(false);
    }


    private void chargeArtistAdapter(final View view, List list){
        if (artistAdapter == null) {
            artistAdapter = new ArtistAdapter(list, getContext(), new AdapterEventInterface() {
                @Override
                public void onClickItem(String name) {
                }
            });
        } else {
            artistAdapter.notifyDataSetChanged();
        }
        rv_artists.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_artists.setAdapter(artistAdapter);
        rv_artists.setNestedScrollingEnabled(false);


    }

    private void chargeAlbumAdapter(final View view, List list){
        System.out.println("charge album" + list.size());
        if (albumAdapter == null) {
            albumAdapter = new AlbumAdapter(list, getContext(), new AdapterEventInterface() {
                @Override
                public void onClickItem(String name) {
                }
            });
        } else {
            albumAdapter.notifyDataSetChanged();
        }
        rv_albums.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_albums.setAdapter(albumAdapter);
        rv_albums.setNestedScrollingEnabled(false);
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
