package screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.panaceasoft.admotors.R;
import com.panaceasoft.admotors.viewobject.Color;

import java.io.DataOutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Review_adapter extends RecyclerView.Adapter<Review_adapter.MyViewHolder> {

    private List<Review_model> modelList;
    private Context context;
    String language;
    SharedPreferences preferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,rating,message,user_name,date;
        public ImageView image1,image2,image3,image4,image5;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            rating=(TextView) view.findViewById(R.id.rating_number);
            message=(TextView) view.findViewById(R.id.msg);
            user_name=(TextView) view.findViewById(R.id.user_name);
            date=(TextView) view.findViewById(R.id.date);
            image1 = (ImageView) view.findViewById(R.id.img_star1);
            image2 = (ImageView) view.findViewById(R.id.img_star2);
            image3 = (ImageView) view.findViewById(R.id.img_star3);
            image4 = (ImageView) view.findViewById(R.id.img_star4);
            image5 = (ImageView) view.findViewById(R.id.img_star5);


        }
    }

    public Review_adapter(List<Review_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Review_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_review, parent, false);

        context = parent.getContext();

        return new Review_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Review_adapter.MyViewHolder holder, int position) {
        Review_model mList = modelList.get(position);

        preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
        language=preferences.getString("language","");

            holder.title.setText(mList.getTitle());
            holder.rating.setText(mList.getStar());
            int k=Integer.parseInt(mList.getStar());
        if(k==1) {
            holder.image1.setColorFilter(context.getResources().getColor(R.color.colorPromary));
        }else if(k==2){
            holder.image1.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            holder.image2.setColorFilter(context.getResources().getColor(R.color.colorPromary));

        }else if(k==3){
            holder.image1.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            holder.image2.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            holder.image3.setColorFilter(context.getResources().getColor(R.color.colorPromary));
        }else if(k==4){
            holder.image1.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            holder.image2.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            holder.image3.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            holder.image4.setColorFilter(context.getResources().getColor(R.color.colorPromary));
        }else if(k==5){
                holder.image1.setColorFilter(context.getResources().getColor(R.color.colorPromary));
                holder.image2.setColorFilter(context.getResources().getColor(R.color.colorPromary));
                holder.image3.setColorFilter(context.getResources().getColor(R.color.colorPromary));
                holder.image4.setColorFilter(context.getResources().getColor(R.color.colorPromary));
                holder.image5.setColorFilter(context.getResources().getColor(R.color.colorPromary));
            }else {
            Toast.makeText(context, "no star yet", Toast.LENGTH_SHORT).show();
        }

        holder.message.setText(mList.getMessage());
        holder.user_name.setText(mList.getUser_name());
        holder.date.setText("On "+mList.getDate());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
