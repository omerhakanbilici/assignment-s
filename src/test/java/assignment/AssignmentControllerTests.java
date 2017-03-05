/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package assignment;

import assignment.model.User;
import assignment.service.AssignmentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AssignmentService assignmentService;

    @Test
    public void registerShouldReturnValidation() throws Exception {

        User user = new User();
        user.setUsername("aa");
        user.setEmail("a@a.com");
        user.setPassword("123AAA");
        List<String> stringList = assignmentService.register(user);
        String result = stringList.get(0);
        Assert.assertEquals(result, "Username should be at least 3 characters.");
    }

    @Test
    public void profileShouldReturnException() throws Exception {

        this.mockMvc.perform(post("/profile").header("Token", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFobWV0IiwiZXhwIjoxNDg4NjcyNzg5fQ.Rm2FG_EgMmSzoEXkzMPstyn1GAzhjf3ja91cWCh90Y8"))
                .andDo(print()).andExpect(status().isUnauthorized());
//                .andExpect(jsonPath("$").value("JWT expired"));
//                .andExpect(jsonPath("$.content").value("Exception"));
    }

}
