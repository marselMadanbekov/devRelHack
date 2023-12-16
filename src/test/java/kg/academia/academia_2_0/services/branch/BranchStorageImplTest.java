package kg.academia.academia_2_0.services.branch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class BranchStorageImplTest {
    @Autowired
    private BranchStorageImpl branchStorage;

    @Test
    void handleGetBranchById_ReturnsBranchWithId() {
        //given
        Branch branch = branchStorage.save(
                Branch.builder()
                        .name("Osh")
                        .state("Osh")
                        .town("Osh")
                        .build());
        Branch branch1 = branchStorage.save(
                Branch.builder()
                        .name("Osh1")
                        .state("Osh1")
                        .town("Osh1")
                        .build());

        //when
        Branch response = branchStorage.getBranchById(branch.getId());

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(branch, response);
    }

//    @Test
//    void handleCreateBranch_ReturnsCreatedBranchWithId() {
//        Branch branch = branchStorage.save(
//                Branch.builder()
//                        .name("Jalal-Abad")
//                        .state("Jalal-Abad")
//                        .town("Jalal-Abad")
//                        .build());
//
//        Assertions.assertNotNull(branch);
//        Assertions.assertNotNull(branch.getId());
//    }
//
//    @Test
//    void deleteBranch() {
//        //given
//        Branch branch = branchStorage.save(
//                Branch.builder()
//                        .name("Osh")
//                        .state("Osh")
//                        .town("Osh")
//                        .build());
//
//        //when
//        branchStorage.deleteById(branch.getId());
//
//        //then
//        Assertions.assertThrows(NoSuchElementException.class, () -> branchStorage.getBranchById(branch.getId()));
//    }
}
