package kg.academia.academia_2_0.services.branch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class BranchServiceImplTest {

    @Autowired
    private BranchService branchService;

    @Test
    void handleCreateBranchRequest_ReturnsCreatedBranch() {
        //given
        BranchCreate branchCreate = BranchCreate.builder()
                .town("Jalal-Abad")
                .state("Jalal-Abad")
                .name("Jalal-Abad")
                .build();

        //when
        Branch branch = branchService.createBranch(branchCreate);


        //then
        assertNotNull(branch.getId());
        assertEquals(branchCreate.getName(), branch.getName());
        assertEquals(branchCreate.getTown(), branch.getTown());
        assertEquals(branchCreate.getState(), branch.getState());
    }
}
