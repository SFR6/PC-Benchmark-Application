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

ofstream fout("pyramid_out.txt", ios::app);

float angle = 0.0;
float scale = 1.0;
float x = -2.9;
float y = -2.9;
float rotation_speed = 0.07;
float scale_speed = 0.005;
float movement_speed = 0.002;

enum State { BOTTOM_LEFT, TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT };
State currentState = BOTTOM_LEFT;

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

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    glTranslatef(x, y, -14.0);
    glRotatef(angle, 0.0, 1.0, 0.0);
    glScalef(scale, scale, scale);

    glBegin(GL_TRIANGLES);

    // Front face
    glColor3f(1.0, 0.0, 0.0);  // Red
    glVertex3f(0.0, 1.0, 0.0);
    glVertex3f(-1.0, -1.0, 1.0);
    glVertex3f(1.0, -1.0, 1.0);

    // Right face
    glColor3f(0.0, 1.0, 0.0);  // Green
    glVertex3f(0.0, 1.0, 0.0);
    glVertex3f(1.0, -1.0, 1.0);
    glVertex3f(1.0, -1.0, -1.0);

    // Back face
    glColor3f(0.0, 0.0, 1.0);  // Blue
    glVertex3f(0.0, 1.0, 0.0);
    glVertex3f(1.0, -1.0, -1.0);
    glVertex3f(-1.0, -1.0, -1.0);

    // Left face
    glColor3f(1.0, 1.0, 0.0);  // Yellow
    glVertex3f(0.0, 1.0, 0.0);
    glVertex3f(-1.0, -1.0, -1.0);
    glVertex3f(-1.0, -1.0, 1.0);

    glEnd();

    // Base
    glBegin(GL_QUADS);

    glColor3f(1.0, 0.0, 1.0); // Purple
    glVertex3f(-1.0, -1.0, -1.0);
    glVertex3f(1.0, -1.0, -1.0);
    glVertex3f(1.0, -1.0, 1.0);
    glVertex3f(-1.0, -1.0, 1.0);

    glEnd();

    glutSwapBuffers();
    angle += rotation_speed;
    if (angle > 360.0)
    {
        angle = 0.0;
    }
    scale += (0.05 * scale_speed);
    if (scale > 1.0 || scale < 0.7)
    {
        scale_speed = -scale_speed;
    }

    switch (currentState) 
    {
        case BOTTOM_LEFT:
        {
            y += movement_speed;
            if (y >= 2.9) 
            {
                y = 2.9;
                currentState = TOP_LEFT;
            }
            break;
        }
        case TOP_LEFT:
        {
            x += movement_speed;
            if (x >= 2.9) 
            {
                x = 2.9;
                currentState = TOP_RIGHT;
            }
            break;
        }
        case TOP_RIGHT:
        {
            y -= movement_speed;
            if (y <= -2.9) 
            {
                y = -2.9;
                currentState = BOTTOM_RIGHT;
            }
            break;
        }
        case BOTTOM_RIGHT:
        {
            x -= movement_speed;
            if (x <= -2.9) 
            {
                x = -2.9;
                currentState = BOTTOM_LEFT;
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
    glutCreateWindow("Transforming Pyramid");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(display);

    glEnable(GL_DEPTH_TEST);

    start_time = high_resolution_clock::now();
    glutMainLoop();

    return 0;
}