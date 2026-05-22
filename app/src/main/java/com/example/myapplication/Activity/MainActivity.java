package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.Frangment.ChatFragment;
import com.example.myapplication.Frangment.DanhbaFragment;
import com.example.myapplication.Frangment.ProfileFragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Kích hoạt hiển thị tràn viền hiện đại
        EdgeToEdge.enable(this);
        
        // 2. Sử dụng View Binding để quản lý UI sạch sẽ hơn
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thiết lập fragment mặc định khi vào app
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_main, new ChatFragment())
                    .commit();
        }

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_chats) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_main, new ChatFragment())
                        .commit();
                return true;
            } else if (itemId == R.id.nav_contacts) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_main, new DanhbaFragment())
                        .commit();
                return true;
            } else if (itemId == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_main, new ProfileFragment())
                        .commit();
                return true;
            }
            return false;
        });

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search) {
                Toast.makeText(this, "Tính năng tìm kiếm đang phát triển", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // 3. Thiết lập Toolbar làm ActionBar của hệ thống
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false); // Ẩn tiêu đề mặc định để dùng tiêu đề tùy chỉnh trong XML
        }

        // 5. Xử lý khoảng cách thanh hệ thống (StatusBar/NavigationBar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // Bottom = 0 vì đã có BottomNav
            return insets;
        });

        // 6. Xử lý nút bấm nổi (FloatingActionButton)
        binding.fab.setOnClickListener(v -> {
            Toast.makeText(this, "Bắt đầu cuộc trò chuyện mới", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Nạp menu tìm kiếm vào Toolbar
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
