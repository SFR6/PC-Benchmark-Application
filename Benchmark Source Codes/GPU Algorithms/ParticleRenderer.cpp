#include <iostream>
#include <fstream>
#include <chrono>
#include <GL/glut.h>
#include <ctime>
#include <cmath>

#ifndef M_PI
    #define M_PI 3.14159265358979323846
#endif

#define ull unsigned long long

using namespace std;
using namespace chrono;

ifstream fin("particles_in.txt");
ofstream fout("particles_out.txt", ios::app);

typedef struct
{
    float x, y, z; // Add z coordinate
    float r, g, b;
    float speedX, speedY, speedZ; // Add speedZ
    float size;
} Particle;

Particle particles[1000];

int MAX_PARTICLES = 500;
float MAX_SIZE = 0.05;
float MIN_SIZE = 0.03;
float COLLISION_DISTANCE = 0.05;
float MOVEMENT_FACTOR = 0.3;

ull frame_count;
high_resolution_clock::time_point start_time;

void initParticles()
{
    int i;
    for (i = 0; i < MAX_PARTICLES; ++i)
    {
        particles[i].x = ((float)(rand() % 200) - 100) / 100.0;
        particles[i].y = ((float)(rand() % 200) - 100) / 100.0;
        particles[i].z = ((float)(rand() % 200) - 100) / 100.0;
        particles[i].r = (float)(rand() % 256) / 255.0;
        particles[i].g = (float)(rand() % 256) / 255.0;
        particles[i].b = (float)(rand() % 256) / 255.0;
        particles[i].speedX = ((float)(rand() % 100) / 1000.0) - 0.05;
        particles[i].speedY = ((float)(rand() % 100) / 1000.0) - 0.05;
        particles[i].speedZ = ((float)(rand() % 100) / 1000.0) - 0.05;
        particles[i].size = ((float)(rand() % (int)((MAX_SIZE - MIN_SIZE) * 100))) / 100 + MIN_SIZE;
    }
}

void drawParticles()
{
    int i;
    for (i = 0; i < MAX_PARTICLES; ++i)
    {
        glColor3f(particles[i].r, particles[i].g, particles[i].b);
        glPushMatrix();
        glTranslatef(particles[i].x, particles[i].y, particles[i].z);
        glutSolidSphere(particles[i].size, 20, 20);
        glPopMatrix();
    }
}

void updateParticles()
{
    int i, j;
    for (i = 0; i < MAX_PARTICLES; i++)
    {
        particles[i].x += particles[i].speedX * MOVEMENT_FACTOR;
        particles[i].y += particles[i].speedY * MOVEMENT_FACTOR;
        particles[i].z += particles[i].speedZ * MOVEMENT_FACTOR;

        // Bounce particles off the window edges
        if (particles[i].x + particles[i].size > 1.0 || particles[i].x - particles[i].size < -1.0)
        {
            particles[i].speedX = -particles[i].speedX;
        }

        if (particles[i].y + particles[i].size > 1.0 || particles[i].y - particles[i].size < -1.0)
        {
            particles[i].speedY = -particles[i].speedY;
        }

        if (particles[i].z + particles[i].size > 1.0 || particles[i].z - particles[i].size < -1.0)
        {
            particles[i].speedZ = -particles[i].speedZ;
        }

        // Bounce particles off each other
        for (j = 0; j < MAX_PARTICLES; ++j)
        {
            if (i != j)
            {
                float dx = particles[i].x - particles[j].x;
                float dy = particles[i].y - particles[j].y;
                float dz = particles[i].z - particles[j].z;
                float distance = sqrt(dx * dx + dy * dy + dz * dz);

                if (distance < COLLISION_DISTANCE)
                {
                    float tempSpeedX = particles[i].speedX;
                    particles[i].speedX = particles[j].speedX;
                    particles[j].speedX = tempSpeedX;

                    float tempSpeedY = particles[i].speedY;
                    particles[i].speedY = particles[j].speedY;
                    particles[j].speedY = tempSpeedY;

                    float tempSpeedZ = particles[i].speedZ;
                    particles[i].speedZ = particles[j].speedZ;
                    particles[j].speedZ = tempSpeedZ;
                }
            }
        }
    }
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glEnable(GL_DEPTH_TEST);
    drawParticles();
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

void update(int value)
{
    updateParticles();
    glutPostRedisplay();
    glutTimerFunc(1, update, 0); // Update as frequently as possible (1 ms)
}

void reshape(int width, int height)
{
    glViewport(0, 0, width, height);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, (float)width / (float)height, 1.0, 100.0);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(0.0, 0.0, -4.0);
}

int main(int argc, char** argv)
{
    srand(time(NULL));

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(700, 700);
    glutCreateWindow("Particle System");

    initParticles();

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutTimerFunc(0, update, 0);

    glClearColor(0.0, 0.0, 0.0, 1.0);

    fin >> MAX_PARTICLES;

    start_time = high_resolution_clock::now();
    glutMainLoop();

    return 0;
}