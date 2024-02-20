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

ifstream fin("cylinder_in.txt");
ofstream fout("cylinder_out.txt", ios::app);

enum State { S0, S1, S2, S3, S4, S5 };
State currentState = S0;

float x = 0.0;
float y = 0.0;
float movement_speed = 0.003;

ull frame_count;
high_resolution_clock::time_point start_time;

typedef struct Color
{
    int red, green, blue;
};

Color calculate_color(double latitudeSin, double longitudeCos, double longitudeSin)
{
    Color color;
    color.red = (int)((latitudeSin + 1.0) * 127.5);
    color.green = (int)((longitudeCos + 1.0) * 127.5);
    color.blue = (int)((longitudeSin + 1.0) * 127.5);
    return color;
}

int sides = 20;

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    float radius = 2.0;
    glTranslatef(x, y, -15.0);

    float cylinder_tilt_angle = 30.0;
    glRotatef(cylinder_tilt_angle, 1.0, 0.0, 0.0);

    float height = 2.0;

    glBegin(GL_QUAD_STRIP);
    int i;
    for (i = 0; i <= sides; ++i) 
    {
        float theta = (2.0 * M_PI * (float)(i)) / (float)(sides);
        float x = radius * cos(theta);
        float z = radius * sin(theta);

        Color color1 = calculate_color(x, height, z);
        Color color2 = calculate_color(x, -height, z);

        glColor3f(color1.red / 255.0, color1.green / 255.0, color1.blue / 255.0);
        glVertex3f(x, height, z);

        glColor3f(color2.red / 255.0, color2.green / 255.0, color2.blue / 255.0);
        glVertex3f(x, -height, z);
    }
    glEnd();

    // Draw the top and bottom surfaces
    glBegin(GL_POLYGON);
    for (i = 0; i <= sides; ++i) 
    {
        float theta = (2.0 * M_PI * (float)(i)) / (float)(sides);
        float x = radius * cos(theta);
        float z = radius * sin(theta);

        Color color1 = calculate_color(x, height, z);
        Color color2 = calculate_color(x, -height, z);

        glColor3f(color1.red / 255.0, color1.green / 255.0, color1.blue / 255.0);
        glVertex3f(x, height, z);

        glColor3f(color2.red / 255.0, color2.green / 255.0, color2.blue / 255.0);
        glVertex3f(x, -height, z);
    }
    glEnd();

    glutSwapBuffers();

    switch (currentState)
    {
        case S0:
        {
            x -= movement_speed;
            y += movement_speed;
            if (x <= -2.9 && y >= 2.9)
            {
                currentState = S1;
            }
            break;
        }
        case S1:
        {
            x += movement_speed;
            y -= movement_speed;
            if (x >= 2.9 && y <= -2.9)
            {
                currentState = S2;
            }
            break;
        }
        case S2:
        {
            x -= movement_speed;
            y += movement_speed;
            if (x <= 0.0 && y >= 0.0)
            {
                currentState = S3;
            }
            break;
        }
        case S3:
        {
            x += movement_speed;
            y += movement_speed;
            if (x >= 2.9 && y >= 2.9)
            {
                currentState = S4;
            }
            break;
        }
        case S4:
        {
            x -= movement_speed;
            y -= movement_speed;
            if (x <= -2.9 && y <= -2.9)
            {
                currentState = S5;
            }
            break;
        }
        case S5:
        {
            x += movement_speed;
            y += movement_speed;
            if (x >= 0.0 && y >= 0.0)
            {
                currentState = S0;
            }
            break;
        }
        default: break;
    }

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
    glutCreateWindow("Moving Cylinder");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(display);

    glEnable(GL_DEPTH_TEST);

    fin >> sides;

    start_time = high_resolution_clock::now();
    glutMainLoop();

    return 0;
}