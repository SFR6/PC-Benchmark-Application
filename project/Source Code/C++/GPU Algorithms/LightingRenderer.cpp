#include <iostream>
#include <fstream>
#include <ctime>
#include <chrono>
#include <GL/glut.h>

#define ull unsigned long long

#ifndef M_PI
    #define M_PI 3.14159265358979323846
#endif

using namespace std;
using namespace chrono;

ifstream fin("light_in.txt");
ofstream fout("light_out.txt", ios::app);

float light_angle = 0.0;
float angle_speed = 0.1;
int change = 1;

ull frame_count;
high_resolution_clock::time_point start_time;

typedef struct Color
{
    int red, green, blue;
};

double to_radians(double degrees)
{
    return (M_PI * degrees) / 180.0;
}

Color calculate_color(double latitudeSin, double longitudeCos, double longitudeSin)
{
    Color color;
    color.red = (int)((latitudeSin + 1.0) * 127.5);
    color.green = (int)((longitudeCos + 1.0) * 127.5);
    color.blue = (int)((longitudeSin + 1.0) * 127.5);
    return color;
}

int torus_sides = 30;
int torus_rings = 30;

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    glTranslatef(0.0, 0.0, -7.0);

    float inner_radius = 0.5;
    float outer_radius = 1.0;
    float torus_tilt_angle = -45.0;

    glPushMatrix();
    glRotatef(torus_tilt_angle, 1.0, 0.0, 0.0);

    int i, j;
    for (i = 0; i < torus_rings - 1; ++i)
    {
        float theta1 = (float)(i * 2 * M_PI / torus_rings);
        float theta2 = (float)((i + 1) * 2 * M_PI / torus_rings);

        glBegin(GL_QUAD_STRIP);

        for (j = 0; j <= torus_sides; ++j)
        {
            float phi = (float)(j * 2 * M_PI / torus_sides);
            float x1 = (outer_radius + inner_radius * (float)cos(theta1)) * (float)cos(phi);
            float y1 = (outer_radius + inner_radius * (float)cos(theta1)) * (float)sin(phi);
            float z1 = inner_radius * (float)sin(theta1);

            float x2 = (outer_radius + inner_radius * (float)cos(theta2)) * (float)cos(phi);
            float y2 = (outer_radius + inner_radius * (float)cos(theta2)) * (float)sin(phi);
            float z2 = inner_radius * (float)sin(theta2);

            // Define colors for each vertex
            Color color1 = calculate_color(x1, y1, z1);
            Color color2 = calculate_color(x2, y2, z2);

            glColor3f(color1.red / 255.0, color1.green / 255.0, color1.blue / 255.0);
            //glColor3f(0.5, 0.5, 0.5);
            glVertex3f(x1, y1, z1);

            glColor3f(color2.red / 255.0, color2.green / 255.0, color2.blue / 255.0);
            //glColor3f(0.5, 0.5, 0.5);
            glVertex3f(x2, y2, z2);

            float normal1[] = { x1 - outer_radius * (float)cos(phi), y1 - outer_radius * (float)sin(phi), z1 };
            float normal2[] = { x2 - outer_radius * (float)cos(phi), y2 - outer_radius * (float)sin(phi), z2 };

            // Normalize normals
            float length1 = sqrt(normal1[0] * normal1[0] + normal1[1] * normal1[1] + normal1[2] * normal1[2]);
            float length2 = sqrt(normal2[0] * normal2[0] + normal2[1] * normal2[1] + normal2[2] * normal2[2]);

            normal1[0] /= length1; 
            normal1[1] /= length1;
            normal1[2] /= length1;

            normal2[0] /= length2; 
            normal2[1] /= length2; 
            normal2[2] /= length2;

            glNormal3fv(normal1);
            glVertex3f(x1, y1, z1);

            glNormal3fv(normal2);
            glVertex3f(x2, y2, z2);
        }

        glEnd();
    }

    glPopMatrix();

    light_angle += angle_speed;

    if (light_angle > 360.0)
    {
        light_angle = 0.0;
        change = -change;
    }

    double lightX, lightY, lightZ;
    double cos_light_angle = cos(to_radians(light_angle));
    double sin_light_angle = sin(to_radians(light_angle));
    if (change == 1)
    {
        lightX = cos_light_angle * 10.0;
        lightY = 10.0;
        lightZ = sin_light_angle * 10.0;
    }
    else
    {
        lightX = 10.0;
        lightY = cos_light_angle * 10.0;
        lightZ = sin_light_angle * 10.0;
    }

    float light_position[] = { (float)lightX, (float)lightY, (float)lightZ, 1.0 };  // 1.0 indicates a positional light
    float light_ambient[] = { 0.2, 0.2, 0.2, 1.0 };
    float light_diffuse[] = { 1.0, 1.0, 1.0, 1.0 };
    float light_specular[] = { 1.0, 1.0, 1.0, 1.0 };

    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glLightfv(GL_LIGHT0, GL_POSITION, light_position);
    glLightfv(GL_LIGHT0, GL_AMBIENT, light_ambient);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, light_diffuse);
    glLightfv(GL_LIGHT0, GL_SPECULAR, light_specular);

    // Enable material properties for the torus
    float mat_ambient_diffuse[] = { 0.5, 0.5, 0.5, 1.0 };
    float mat_specular[] = { 1.0, 1.0, 1.0, 1.0 };
    float mat_shininess[] = { 50.0 };

    //glEnable(GL_COLOR_MATERIAL);

    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, mat_ambient_diffuse);
    glMaterialfv(GL_FRONT, GL_SPECULAR, mat_specular);
    glMaterialfv(GL_FRONT, GL_SHININESS, mat_shininess);

    glEnable(GL_COLOR_MATERIAL);
    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

    // Set the light model to local viewer
    float values[] = { 1.0 };
    glLightModelfv(GL_LIGHT_MODEL_LOCAL_VIEWER, values);
    glutSwapBuffers();

    ++frame_count;

    high_resolution_clock::time_point end_time = high_resolution_clock::now();
    duration<double> elapsed_time = duration_cast<duration<double>>(end_time - start_time);
    if (elapsed_time.count() > 5.0)
    {
        fout << frame_count / 5 << " ";

        fin.close();
        fout.close();
        exit(0);
    }
}

void reshape(int w, int h)
{
    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, (double)w / (double)h, 1.0, 200.0);
    glMatrixMode(GL_MODELVIEW);
}

int main()
{
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(700, 700);
    glutCreateWindow("Lighting Torus");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(display);

    glEnable(GL_DEPTH_TEST);

    fin >> torus_sides >> torus_rings;

    start_time = high_resolution_clock::now();
    glutMainLoop();

    return 0;
}