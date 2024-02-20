#include <iostream>
#include <fstream>
#include <chrono>
#include <GL/glut.h>

#define ull unsigned long long

using namespace std;
using namespace chrono;

ofstream fout("cube_out.txt", ios::app);

float angle;
float sign1 = 1.0;
float sign2 = 1.0;
float angle_speed = 0.07;

ull frame_count;
high_resolution_clock::time_point start_time;

void display() 
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    glTranslatef(0.0, 0.0, -7.0);
    glRotatef(angle, sign1 * 0.5, sign2 * 1.0, 0.0);

    glBegin(GL_QUADS);

    // Front Face
    glColor3f(1.0, 0.0, 0.0); // Red
    glVertex3f(-1.0, -1.0, 1.0);
    glVertex3f(1.0, -1.0, 1.0);
    glVertex3f(1.0, 1.0, 1.0);
    glVertex3f(-1.0, 1.0, 1.0);

    // Back Face
    glColor3f(0.0, 1.0, 0.0); // Green
    glVertex3f(-1.0, -1.0, -1.0);
    glVertex3f(1.0, -1.0, -1.0);
    glVertex3f(1.0, 1.0, -1.0);
    glVertex3f(-1.0, 1.0, -1.0);

    // Left Face
    glColor3f(0.0, 0.0, 1.0); // Blue
    glVertex3f(-1.0, -1.0, -1.0);
    glVertex3f(-1.0, 1.0, -1.0);
    glVertex3f(-1.0, 1.0, 1.0);
    glVertex3f(-1.0, -1.0, 1.0);

    // Right Face
    glColor3f(1.0, 1.0, 0.0); // Yellow
    glVertex3f(1.0, -1.0, -1.0);
    glVertex3f(1.0, 1.0, -1.0);
    glVertex3f(1.0, 1.0, 1.0);
    glVertex3f(1.0, -1.0, 1.0);

    // Top Face
    glColor3f(1.0, 0.0, 1.0); // Purple
    glVertex3f(-1.0, 1.0, -1.0);
    glVertex3f(1.0, 1.0, -1.0);
    glVertex3f(1.0, 1.0, 1.0);
    glVertex3f(-1.0, 1.0, 1.0);

    // Bottom Face
    glColor3f(0.0, 1.0, 1.0); // Cyan
    glVertex3f(-1.0, -1.0, -1.0);
    glVertex3f(1.0, -1.0, -1.0);
    glVertex3f(1.0, -1.0, 1.0);
    glVertex3f(-1.0, -1.0, 1.0);

    glEnd();

    glutSwapBuffers();
    angle += angle_speed;
    if (angle > 360.0)
    {
        angle = 0.0;
        if (sign1 == 1.0 && sign2 == 1.0)
        {
            sign2 = -sign2;
        }
        else if (sign1 == 1.0 && sign2 == -1.0)
        {
            sign1 = -sign1;
        }
        else if (sign1 == -1.0 && sign2 == -1.0)
        {
            sign2 = -sign2;
        }
        else
        {
            sign1 = -sign1;
        }
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
    glutCreateWindow("Rotating Cube");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(display);

    glEnable(GL_DEPTH_TEST);

    start_time = high_resolution_clock::now();
    glutMainLoop();

    return 0;
}