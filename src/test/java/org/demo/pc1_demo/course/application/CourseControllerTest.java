package org.demo.pc1_demo.course.application;

import org.demo.pc1_demo.teacher.infrastructure.TeacherRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;


    String jsonTeacher = "{ \"name\": \"jhon\", \"email\": \"jhon@gmail.com\", \"password\": \"password\", \"isTeacher\": true }";

    String token = "";

    @BeforeEach
    public void setUp() throws Exception {
        //Si quieren ver los cambios en su base de datos, pueden la siguiente línea
        teacherRepository.deleteAll();

        //Create a student
        mockMvc.perform(post("/auth/register")
                .contentType(APPLICATION_JSON)
                .content("{ \"name\": \"Jane\", \"email\": \"jane@gmail.com\", \"password\": \"password\" }"));

        //Create a teacher
        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonTeacher))
                .andReturn();

        //Get the token
        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);

        //Create a course
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content("{ \"title\": \"Math\"}"));

        //Create a course
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content("{ \"title\": \"Comu\"}"));
    }

    //Test the following endpoints

    //    @Get
    //    public ResponseEntity<List<Course>> ListarCursosConVacantes()

    @Test
    public void ListarCursosConVacantes() throws Exception {
        mockMvc.perform(get("/course")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    //    @Patch
    //    public ResponseEntity<Void> InscribirAlumno(@PathVariable Long courseId, @PathVariable Long studentId) {

    @Test
    public void InscribirAlumno() throws Exception {
        mockMvc.perform(patch("/course/1/student/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    //    @Delete
    //    public ResponseEntity<Void> DesinscribirAlumno(@PathVariable Long courseId, @PathVariable Long studentId) {

    @Test
    public void DesinscribirAlumno() throws Exception {

        InscribirAlumno();

        mockMvc.perform(delete("/course/1/student/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    //    @Get
    //    public ResponseEntity<List<Student>> ListarAlumnosInscritos(@PathVariable Long id) {

    @Test
    public void ListarAlumnosInscritos() throws Exception {

        InscribirAlumno();

        var res = mockMvc.perform(get("/course/1/students")
                .header("Authorization", "Bearer " + token))
                .andReturn();

        System.out.println(res.getResponse().getContentAsString());

        ResultMatcher status = status().isOk();
        status.match(res);
    }

    //    @Post
    //    public ResponseEntity<Course> CrearCurso(@RequestBody Course course) {

    @Test
    public void CrearCurso() throws Exception {
        mockMvc.perform(post("/course")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content("{ \"title\": \"Physics\"}"))
                .andExpect(status().isOk());
    }

    //    @Patch
    //    public ResponseEntity<Course> ActualizarCurso(@PathVariable Long id, @RequestBody Course course) {

    @Test
    public void ActualizarCurso() throws Exception {
        mockMvc.perform(patch("/course/1")
                .header("Authorization", "Bearer " + token)
                .contentType(APPLICATION_JSON)
                .content("{ \"title\": \"Math\"}"))
                .andExpect(status().isOk());
    }


    //    @Delete
    //    public ResponseEntity<Void> EliminarCurso(@PathVariable Long id) {

    @Test
    public void EliminarCurso() throws Exception {
        //Si corren todos los test, este test fallará porque el curso va a estar tener un estudiante

        mockMvc.perform(delete("/course/2")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }



}
