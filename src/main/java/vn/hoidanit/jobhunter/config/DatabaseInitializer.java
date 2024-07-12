package vn.hoidanit.jobhunter.config;

import lombok.Builder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.entity.Permission;
import vn.hoidanit.jobhunter.domain.entity.Role;
import vn.hoidanit.jobhunter.domain.entity.User;
import vn.hoidanit.jobhunter.repository.PermissionRepository;
import vn.hoidanit.jobhunter.repository.RoleRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;
import vn.hoidanit.jobhunter.util.Enum.GenderEnum;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class DatabaseInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countPermissions = this.permissionRepository.count();
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();

        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();
            arr.add(Permission.builder().method("POST").apiPath("/api/v1/companies").name("Create a company").module("COMPANIES").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/companies").name("Update a company").module("COMPANIES").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/companies/{id}").name("Delete a company").module("COMPANIES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/companies/{id}").name("Get a company by id").module("COMPANIES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/companies").name("Get companies with pagination").module("COMPANIES").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/jobs").name("Create a job").module("JOBS").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/jobs").name("Update a job").module("JOBS").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/jobs/{id}").name("Delete a job").module("JOBS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/jobs/{id}").name("Get a job by id").module("JOBS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/jobs").name("Get jobs with pagination").module("JOBS").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/permissions").name("Create a permission").module("PERMISSIONS").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/permissions").name("Update a permission").module("PERMISSIONS").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/permissions/{id}").name("Delete a permission").module("PERMISSIONS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/permissions/{id}").name("Get a permission by id").module("PERMISSIONS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/permissions").name("Get permissions with pagination").module("PERMISSIONS").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/resumes").name("Create a resume").module("RESUMES").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/resumes").name("Update a resume").module("RESUMES").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/resumes/{id}").name("Delete a resume").module("RESUMES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/resumes/{id}").name("Get a resume by id").module("RESUMES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/resumes").name("Get resumes with pagination").module("RESUMES").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/roles").name("Create a role").module("ROLES").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/roles").name("Update a role").module("ROLES").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/roles/{id}").name("Delete a role").module("ROLES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/roles/{id}").name("Get a role by id").module("ROLES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/roles").name("Get roles with pagination").module("ROLES").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/users").name("Create a user").module("USERS").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/users").name("Update a user").module("USERS").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/users/{id}").name("Delete a user").module("USERS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/users/{id}").name("Get a user by id").module("USERS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/users").name("Get users with pagination").module("USERS").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/subscribers").name("Create a subscriber").module("SUBSCRIBERS").build());

            arr.add(Permission.builder().method("PUT").apiPath("/api/v1/subscribers").name("Update a subscriber").module("SUBSCRIBERS").build());

            arr.add(Permission.builder().method("DELETE").apiPath("/api/v1/subscribers/{id}").name("Delete a subscriber").module("SUBSCRIBERS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/subscribers/{id}").name("Get a subscriber by id").module("SUBSCRIBERS").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/subscribers").name("Get subscribers with pagination").module("SUBSCRIBERS").build());

            arr.add(Permission.builder().method("POST").apiPath("/api/v1/files").name("Download a file").module("FILES").build());

            arr.add(Permission.builder().method("GET").apiPath("/api/v1/files").name("Upload a file").module("FILES").build());

            this.permissionRepository.saveAll(arr);
        }

        if (countRoles == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();
            adminRole.setName("SUPER_ADMIN");
            adminRole.setDescription("Admin thì full permissions");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);
        }

        if (countUsers == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAddress("hn");
            adminUser.setAge(25);
            adminUser.setGender(GenderEnum.MALE);
            adminUser.setName("I'm super admin");
            adminUser.setPassword(this.passwordEncoder.encode("123456"));

            Role adminRole = this.roleRepository.findByName("SUPER_ADMIN");
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }

            this.userRepository.save(adminUser);
        }

        if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else System.out.println(">>> END INIT DATABASE");
    }
}
