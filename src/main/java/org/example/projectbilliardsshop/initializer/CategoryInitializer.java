package org.example.projectbilliardsshop.initializer;

import org.example.projectbilliardsshop.model.Category;
import org.example.projectbilliardsshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem có dữ liệu hay chưa, nếu chưa thì thêm dữ liệu mẫu
        if (categoryRepository.count() == 0) {
            // Tạo các category cha và con
            Category home = new Category("HOME", "home");
            Category about = new Category("ABOUT", "about");
            Category cueStick = new Category("CUE STICK", "cue-stick");
            Category poolTables = new Category("POOLS TABLE", "pools-table");
            Category cueCase = new Category("CUE CASE", "cue-case");
            Category billiardAccessories  = new Category("BILLIARD ACCESSORIES", "billiard-accessories");
            Category news  = new Category("NEWS", "news");
            Category services = new Category("SERVICES", "services");
            Category contact = new Category("CONTACT", "contact");
            Category jumpCue = new Category("Jump Cue", "jump-cue");
            Category breakCue = new Category("Break Cue", "break-cue");
            Category standardCue = new Category("Standard Cue", "standard-cue");
            Category poolTable = new Category("Pool Table", "pool-table");
            Category caromTable = new Category("Carom Table", "carom-table");
            Category snookerTable = new Category("Snooker Table", "snooker-table");
            Category softCueCases = new Category("Soft Cue Cases", "soft-cue-cases");
            Category hardCueCases = new Category("Hard Cue Cases", "hard-cue-cases");
            Category billiardGloves = new Category("Billiard gloves", "billiard-gloves");
            Category billiardChalk = new Category("Billiard chalk", "billiard-chalk");
            Category rack = new Category("Rack", "rack");
            Category cueTip = new Category("Cue Tip", "cue-tip");
            Category poolTableFeltReplacement = new Category("Pool table felt replacement", "pool-table-felt-replacement");
            Category cueTipReplacement = new Category("Cue tip replacement", "cue-tip-replacement");


            // Thiết lập mối quan hệ cha-con
            home.setParent(null);// Home là mục cha gốc
            about.setParent(null);
            cueStick.setParent(null);
            poolTable.setParent(poolTables);
            cueCase.setParent(null);
            poolTables.setParent(null);
            billiardAccessories.setParent(null);
            news.setParent(null);
            services.setParent(null);
            contact.setParent(null);
            jumpCue.setParent(cueStick);
            breakCue.setParent(cueStick);
            standardCue.setParent(cueStick);
            caromTable.setParent(poolTables);
            snookerTable.setParent(poolTables);
            softCueCases.setParent(cueCase);
            hardCueCases.setParent(cueCase);
            billiardGloves.setParent(billiardAccessories);
            billiardChalk.setParent(billiardAccessories);
            rack.setParent(billiardAccessories);
            cueTip.setParent(billiardAccessories);
            poolTableFeltReplacement.setParent(services);
            cueTipReplacement.setParent(services);


            // Lưu các category vào cơ sở dữ liệu
            categoryRepository.save(home);
            categoryRepository.save(about);
            categoryRepository.save(cueStick);
            categoryRepository.save(poolTables);
            categoryRepository.save(cueCase);
            categoryRepository.save(billiardAccessories);
            categoryRepository.save(news);
            categoryRepository.save(services);
            categoryRepository.save(contact);
            categoryRepository.save(jumpCue);
            categoryRepository.save(breakCue);
            categoryRepository.save(standardCue);
            categoryRepository.save(poolTable);
            categoryRepository.save(caromTable);
            categoryRepository.save(snookerTable);
            categoryRepository.save(softCueCases);
            categoryRepository.save(hardCueCases);
            categoryRepository.save(billiardGloves);
            categoryRepository.save(billiardChalk);
            categoryRepository.save(rack);
            categoryRepository.save(cueTip);
            categoryRepository.save(poolTableFeltReplacement);
            categoryRepository.save(cueTipReplacement);


            System.out.println("Dữ liệu mẫu cho bảng Category đã được khởi tạo.");
        }
    }
}
